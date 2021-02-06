package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    CompensationRepository compensationRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public String create(Compensation compensation) throws Exception {
        LOG.debug("Creating employee compensation [{}]", compensation);

        Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployeeId());
        if(employee == null){
            throw new Exception("The employee does not exist");
        }

        if(compensationRepository.findByEmployeeId(compensation.getEmployeeId()) != null){
            throw new Exception("The compensation details for the employee already exists");
        }

        compensationRepository.insert(compensation);
        return compensation.getEmployeeId();
    }

    @Override
    public Compensation read(String id) throws Exception {
        Compensation compensation = compensationRepository.findByEmployeeId(id);
        if(compensation == null){
            throw new Exception("The compensation for the employee does not exist");
        }
        compensation.setEmployee(employeeRepository.findByEmployeeId(id));
        return compensation;
    }
}

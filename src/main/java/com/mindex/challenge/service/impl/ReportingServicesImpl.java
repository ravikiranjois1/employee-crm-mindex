package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class ReportingServicesImpl implements ReportingService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String employeeId) {
        int numReportees = 0;
        Queue<Employee> reporteesQueue = new LinkedList<>();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);

        numReportees = directReportInitialize(employee, numReportees, reporteesQueue);

        while(!reporteesQueue.isEmpty()){
            employeeId = reporteesQueue.poll().getEmployeeId();
            employee = employeeRepository.findByEmployeeId(employeeId);
            numReportees = directReportInitialize(employee, numReportees, reporteesQueue);
        }
        reportingStructure.setNumberOfReports(numReportees);
        return reportingStructure;
    }

    public int directReportInitialize(Employee employee, int numReportees, Queue<Employee> reporteesQueue){
        if(employee.getDirectReports() != null){
            numReportees += employee.getDirectReports().size();
            reporteesQueue.addAll(employee.getDirectReports());
        }
        return numReportees;
    }
}

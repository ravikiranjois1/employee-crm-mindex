package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

public interface CompensationService {
    String create(Compensation compensation) throws Exception;
    Compensation read(String id) throws Exception;
}

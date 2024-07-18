package com.pancho.demo.service;

import com.pancho.demo.model.Operation;
import com.pancho.demo.model.RecordOperation;
import com.pancho.demo.persistence.OperationRepository;
import com.pancho.demo.persistence.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRecordService {

    @Autowired
    RecordRepository recordRepository;

    public Iterable<RecordOperation> findAll() {
        return recordRepository.findAll();
    }

}

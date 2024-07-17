package com.pancho.demo.persistence;

import com.pancho.demo.model.Operation;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepository extends CrudRepository<Operation, Long> {

    Operation findByOperationType(String operationType);

}

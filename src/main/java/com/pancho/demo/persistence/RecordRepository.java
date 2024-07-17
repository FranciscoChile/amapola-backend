package com.pancho.demo.persistence;

import com.pancho.demo.model.RecordOperation;
import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<RecordOperation, Long>  {
}

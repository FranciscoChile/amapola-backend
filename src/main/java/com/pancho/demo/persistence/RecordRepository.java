package com.pancho.demo.persistence;

import com.pancho.demo.model.RecordOperation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<RecordOperation, Long>  {

    @Query("SELECT u FROM RecordOperation u WHERE u.visible = true")
    Iterable<RecordOperation> findAllActiveRecords();

}

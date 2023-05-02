package com.databull.api.repository;

import com.databull.api.entity.mysql.DpDestinationTableMetadatum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationTableMetadataRepository extends CrudRepository<DpDestinationTableMetadatum, Long> {
}

package com.databull.api.repository;

import com.databull.api.entity.mysql.DpTablesConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TablesConfigurationRepository extends JpaRepository<DpTablesConfiguration, Long> {
     default void add(DpTablesConfiguration dpTablesConfiguration) {
        this.save(dpTablesConfiguration);
    }
}

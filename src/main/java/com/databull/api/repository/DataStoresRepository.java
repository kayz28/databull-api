package com.databull.api.repository;

import com.databull.api.entity.mysql.DpDataStoresConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataStoresRepository extends CrudRepository<DpDataStoresConfiguration, Long> {

    List<DpDataStoresConfiguration> findByOrgId(Long orgId);

    @Query("select count(d) from DpDataStoresConfiguration d where d.orgId = ?1")
    long countByOrgId(Integer orgId);

    @Query("select d from DpDataStoresConfiguration d where d.id = ?1 and d.type = ?2")
    DpDataStoresConfiguration findByIdAndSourceType(Long id, String sourceType);

    @Query("select d from DpDataStoresConfiguration d where d.type = ?1")
    List<DpDataStoresConfiguration> findBySourceType(String sourceType);

}

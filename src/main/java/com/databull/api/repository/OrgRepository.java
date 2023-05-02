package com.databull.api.repository;

import com.databull.api.entity.mysql.DpOrgDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgRepository extends CrudRepository<DpOrgDetails, Long> {

    @Query("select orgName from DpOrgDetails d where d.id = ?1")
    String findOrgNameById(Long orgId);
}

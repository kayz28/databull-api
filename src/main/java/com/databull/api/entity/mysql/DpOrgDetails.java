package com.databull.api.entity.mysql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "dp_org_details", schema = "data_platform")
@Getter
@Setter
public class DpOrgDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "user_name")
    private String commonUserName;

    @Column(name = "password")
    private String commonPassword;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "pipeline_type_in_use")
    private List<String> pipelineInUse;

}

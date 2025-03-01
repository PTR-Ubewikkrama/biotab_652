package com.test_jig.test_jig_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "final_assembly")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinalAssembly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "device_code")
    private String deviceCode;

    private String category;

    @Column(name = "bladder_code")
    private String bladderCode;

    @Column(name = "upl_number")
    private String uplNumber;

    @Column(name = "udi_number")
    private String udiNumber;

    @Column(name = "adapter_code")
    private String adapterCode;

    @Column(name = "cartoon_number")
    private String cartoonNumber;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_by")
    private String createdBy;
}

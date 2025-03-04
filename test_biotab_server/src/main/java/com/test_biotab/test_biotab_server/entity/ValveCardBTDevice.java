package com.test_biotab.test_biotab_server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "valve_card_bt_device")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValveCardBTDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "bt_device_code")
    private int btDeviceCode;

    @Column(name = "valve_code")
    private String valveCode;

    @Column(name = "valve_code_status")
    private String valveCodeStatus;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "created_by")
    private String createdBy;
}

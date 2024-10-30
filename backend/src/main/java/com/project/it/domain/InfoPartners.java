package com.project.it.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "info_partners")
public class InfoPartners { //협력사 등 회사 정보 저장용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    
    private String comName;
    private String coNum;
    private String phone;
    private String site;
    private String address;
    private String bizType;
}

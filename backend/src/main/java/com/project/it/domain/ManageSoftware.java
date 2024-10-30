package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_assetSW")
@Getter
@ToString(exclude = "license")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManageSoftware extends ManageCategory { //물적자원_category : computer/software
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate regDate; //구입일(등록일)
    private int totalCount; //전체(구입)개수
    private int useCount; //현재 사용중 개수
    private String inCharge;//책임자(등록자 email)
    
    private boolean deleteOrNot; //삭제처리 여부

    @ManyToOne
    @JoinColumn( name = "num_license")
    private AssetLicense assetLicense; //라이센스 정보

    //변경용 메서드
    public void changeTotalCount(int totalCount){
        this.totalCount = totalCount;
    }
    public void changeUseCount(int useCount){
        this.useCount = useCount;
    }
    public void changeInCharge(String inCharge){
        this.inCharge = inCharge;
    }
    public void changeDeleteOrNot(boolean deleteOrNot){
        this.deleteOrNot = deleteOrNot;
    }

}

//Hibernate:
//        create table tbl_assetsw (
//        id bigint not null auto_increment,
//        level_1 varchar(255),
//        level_2 varchar(255),
//        level_3 varchar(255),
//        in_charge varchar(255),
//        reg_date datetime(6),
//        total_count integer not null,
//        use_count integer not null,
//        num_license bigint,
//        primary key (id)
//        ) engine=InnoDB
//        Hibernate:
//        alter table if exists tbl_license
//        add column version varchar(255)
//        Hibernate:
//        alter table if exists tbl_assetsw
//        add constraint FK5essntuaep54e1xlxxobykpwh
//        foreign key (num_license)
//        references tbl_license (id)

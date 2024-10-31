package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "license_asset")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"licenseFiles", "license"})
public class AssetLicense extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano; //관리번호

    private String type; //권리유형 : 자사특허, (타사)사용권

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus; //계약구분(신규, 재계약, 갱신..)
    private LocalDate contractDate; //취득일(계약일)
    private LocalDate expireDate; //만료일
    private int contractCount; //상품구입 개수

    @Lob
    private String comment; //기타 설명

    private boolean expireYN; //만료여부
    private boolean deleteOrNot; //삭제처리 여부

    //@Builder.Default
  //  private List<InfoFile> licenseFiles = new ArrayList<>(); //계약관련 파일

    @OneToOne
    @JoinColumn(name = "license_id")
    private InfoLicense license; //관련 라이선스 상품

    //Method
    //파일 추가
  //  public void addFile(InfoFile infoFile){
  //      this.licenseFiles.add(infoFile);
    //}

}

//    create table license_asset (
//        ano bigint not null auto_increment,
//        reg_date date,
//        modified_by varchar(255),
//    update_date date,
//    created_by varchar(255),
//    comment tinytext,
//    contract_count integer not null,
//        contract_date date,
//        contract_status enum ('CANCEL','EXPIRE','EXTENSION','NEW','RENEWAL'),
//        delete_or_not bit not null,
//        expire_date date,
//        expireyn bit not null,
//        type varchar(255),
//        license_id bigint,
//        primary key (ano)
//        ) engine=InnoDB
//alter table if exists license_asset
//        drop index if exists UK_e7bky6m5cqc2nyh2l8pccviw7
//        Hibernate:
//        alter table if exists license_asset
//        add constraint UK_e7bky6m5cqc2nyh2l8pccviw7 unique (license_id)

//    alter table if exists license_asset
//        add constraint FKo5jd6vg7vc33g3rlddcowkbhb
//        foreign key (license_id)
//        references license_info (lno)
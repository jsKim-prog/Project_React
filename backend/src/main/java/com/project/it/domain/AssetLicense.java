package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "asset_license")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AssetLicense extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano; //관리번호

    private String type; //권리유형 : 자사특허, (타사)사용권

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus; //계약구분(신규, 재계약, 갱신..)
    private LocalDate contractDate; //취득일(계약일)
    private LocalDate expireDate; //만료일

    @Lob
    private String comment; //기타 설명

    private boolean expireYN; //만료여부
    private boolean deleteOrNot; //삭제처리 여부
    @ElementCollection
    @Builder.Default
    private List<FileLicense> fileList = new ArrayList<>(); //계약관련 파일

    @OneToOne
    @JoinColumn(name = "license_id")
    private InfoLicense license; //관련 라이선스 상품

    //Method
    //파일 추가
    public void addFile(FileLicense fileLicense){
        this.fileList.add(fileLicense);
    }

}

//Hibernate:
//        create table tbl_license (
//        id bigint not null auto_increment,
//        comment varchar(255),
//        contract_date datetime(6),
//        expire_date datetime(6),
//        expireyn bit not null,
//        max_user_count integer not null,
//        price_unit varchar(255),
//        purpose varchar(255),
//        right_name varchar(255),
//        total_price integer not null,
//        type varchar(255),
//        unit_price integer not null,
//        primary key (id)
//        ) engine=InnoDB
//        Hibernate:
//        alter table if exists license_file_list
//        add constraint FKfesbse7ih3i82ts192s0piqll
//        foreign key (license_id)
//        references tbl_license (id)

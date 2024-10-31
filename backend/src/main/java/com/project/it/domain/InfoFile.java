package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "file_info")
@ToString(exclude = "categories")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoFile { //라이선스 관련 증빙 파일, 설치파일 등 파일 리스트
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<FileCategory> categories = new ArrayList<>(); //분류
    

    private String title; //파일제목
    private String fileName; //파일이름
    private String folderPath; //폴더경로

    //Method
    public void addCategory(FileCategory fileCategory){
        categories.add(fileCategory);
    }
    public void clearCategory(){
        categories.clear();
    }
}

//Hibernate:
//        create table file_info (
//        fno bigint not null auto_increment,
//        file_class enum ('EVIDENCE','INSTALLATION','REFERENCE','RESULT'),
//        file_name varchar(255),
//        folder_path varchar(255),
//        primary key (fno)
//        ) engine=InnoDB
//        Hibernate:
//        create table info_file_categories (
//        info_file_fno bigint not null,
//        categories tinyint check (categories between 0 and 2)
//        ) engine=InnoDB
//        Hibernate:
//        alter table if exists license_info
//        add column delete_or_not bit default false not null
//        Hibernate:
//        create index index_license_cate
//        on license_info (purpose)
//        Hibernate:
//        alter table if exists info_file_categories
//        add constraint FK4s6447ns1kr4x33avk6r4nrdc
//        foreign key (info_file_fno)
//        references file_info (fno)
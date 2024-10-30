package com.project.it.domain;


import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass //카테고리-상속으로 처리
@Getter
@Setter
@ToString
public class ManageCategory { //품목별 카테고리
    private String level_1; //최상위 : 사무용품, 소프트웨어..
    private String level_2; //하위 : 노트북, 데스크탑...
    private String level_3; //하위2
}

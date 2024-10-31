package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "license_info", indexes = {@Index(name = "index_license_cate", columnList = "purpose")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
public class InfoLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno; //관리번호

    private String rightName; //이름(특허/계약명) : ex_Adobe Photoshop
    private String version; //제품 버전
    private String purpose; //용도 : 디자인, 개발..
    private String copyrightHolder; //저작권자
    private int totalPrice; //금액(계약총액)
    private String priceUnit; //계약단위(월단위, 년단위, 인원수 단위...)
    private int maxUserCount; //최대 사용 가능 인원
    private String contact; //이용경로

    @ColumnDefault("false")
    private boolean deleteOrNot; //삭제 여부

    //변경용 method(금액, 단위, 사용가능인원, 이용경로)
    public void changePrice(int totalPrice){
        this.totalPrice = totalPrice;
    }
    public void changeUnit(String priceUnit){
        this.priceUnit = priceUnit;
    }
    public void changeUserCount(int maxUserCount){
        this.maxUserCount = maxUserCount;
    }
    public void changeContact(String contact){
        this.contact = contact;
    }
    public void changeDeleteState(boolean deleteOrNot){
        this.deleteOrNot = deleteOrNot;
    }


}
//    create table license_info (
//        lno bigint not null auto_increment,
//        contact varchar(255),
//    copyright_holder varchar(255),
//    max_user_count integer not null,
//        price_unit varchar(255),
//        purpose varchar(255),
//        right_name varchar(255),
//        total_price integer not null,
//        version varchar(255),
//        primary key (lno)
//        ) engine=InnoDB
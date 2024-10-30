package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "info_license")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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


}

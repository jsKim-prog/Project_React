package com.project.it.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoMaker { //컴퓨터, 주변기기 기초 정보
    private String maker; //제조사(HP, samsung...)
    private String productNum; //품번(모델번호)
    private String serialNum; //시리얼 번호
}

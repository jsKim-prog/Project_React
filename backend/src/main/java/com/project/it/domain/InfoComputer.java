package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_info_Computer")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoComputer { //컴퓨터 사양 정보 입력용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpu;
    private String gpu;
    private String ram;
    private String storage;
    private String capacity; //총 저장공간 용량
}

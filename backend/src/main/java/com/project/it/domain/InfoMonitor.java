package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_info_Monitor")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InfoMonitor { //모니터 사양 정보 입력용
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resolution ; //해상도(Full-hd, 4K..)
    private int size; //모나터 크기(xx인치)
}

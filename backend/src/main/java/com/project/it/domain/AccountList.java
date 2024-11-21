package com.project.it.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "account_list", indexes = {@Index(name = "idx_account_site", columnList = "site")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountList { //사용경로, 계정만 관리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long acNum; //관리번호

    private String site;
    private String id;
    private String pw;


}

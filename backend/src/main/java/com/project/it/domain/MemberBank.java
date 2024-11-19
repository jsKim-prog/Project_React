
package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_memberBank")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberBank {

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<BankCode> bank_code_List = new ArrayList<>();
    // 은행별 코드
    private String account;
    // 계좌
    private String account_name;
    // 계좌 주인 이름
    @Id
    @JoinColumn(name="member_mno")
    private Long mno;

    @ManyToOne
    private Member member;


}


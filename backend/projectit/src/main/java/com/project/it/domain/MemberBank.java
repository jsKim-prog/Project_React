
package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "memberBank")
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberBank {


    private BankCode bank_code;
    // 은행별 코드
    private String account;
    // 계좌
    private String account_name;
    // 계좌 주인 이름
    @Id
    @JoinColumn(name="member_mno")
    private int mno;


}


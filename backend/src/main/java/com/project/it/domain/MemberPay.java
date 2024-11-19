package com.project.it.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_memberPay")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberPay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pId;

    @ManyToOne
    @JoinColumn(name = "member_mno")
    private Member member ;


}

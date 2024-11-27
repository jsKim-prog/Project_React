package com.project.it.dto;

import com.project.it.constant.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountObjectIPDTO {
    private String ac_id;
    private String ac_pw;
    private AccountType accountType;
}

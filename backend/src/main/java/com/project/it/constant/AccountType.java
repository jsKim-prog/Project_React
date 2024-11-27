package com.project.it.constant;

import com.project.it.domain.converter.StatusConverter;
import jakarta.persistence.Converter;

public enum AccountType implements EnumMapperType{
    REGISTER("등록자"),
    DERIVATION("사용자");

    private String desc;

    //생성자
    private AccountType(String desc){
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Converter(autoApply = true)
    public static class AccountTypeConverter extends StatusConverter<AccountType> {
        public static final String ENUM_NAME = "account_type";
        public AccountTypeConverter() {
            super(AccountType.class, ENUM_NAME, false);
        }
    }
}

package com.project.it.constant;

import com.project.it.domain.converter.StatusConverter;

import java.util.Arrays;

public enum ContractStatus implements EnumMapperType{ //계약 상태
    NEW("신규계약"), //신규
    EXTENSION("연장계약"), //연장
    RENEWAL("재계약"), //재계약
    EXPIRE("계약만료"), //만료
    CANCEL("계약해지"); //해지

    private String desc;
    private ContractStatus(String desc) {
        this.desc = desc;
    }


    @Override
    public String getDesc() { //String 으로 배출
        return desc;
    }


    public static class ContractStatusConverter extends StatusConverter<ContractStatus> {
        public static final String ENUM_NAME = "contract_status";
        public ContractStatusConverter() {
            super(ContractStatus.class, ENUM_NAME, false);
        }
    }
}

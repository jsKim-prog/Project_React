package com.project.it.constant;

import java.util.Arrays;

public enum UsePurpose implements EnumMapperType{//사용목적
    OFFICE("문서/사무"),
    DEVELOPMENT("개발"),
    DESIGN("디자인"),
    BUSINESS("경영/회계"),
    NETWORK("네트워크/보안"),
    ETC("기타")
    ;

    private String desc;

    private UsePurpose(String desc){
        this.desc=desc;
    }

    @Override
    public String getDesc() { //String 으로 배출
        return desc;
    }

    @Override
    public UsePurpose parse(String desc) {
        return Arrays.stream(values()).filter(s->s.desc==desc)
                .findFirst().orElse(UsePurpose.ETC);
    }
}

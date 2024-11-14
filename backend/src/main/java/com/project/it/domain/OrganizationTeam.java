package com.project.it.domain;


public enum OrganizationTeam {
    AWAIT,                  //대기
    TECHNIC,                //기술부
    PERSONNEL,              //인사부
    ACCOUNTING,             //회계부
    FINANCIAL_MANAGEMENT;    //재무관리팀

    // 문자열을 enum으로 변환하는 메서드
    public static OrganizationTeam fromString(String role) {
        for (OrganizationTeam r : OrganizationTeam.values()) {
            if (r.name().equalsIgnoreCase(role)) {  // 대소문자 구분 없이 비교
                return r;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + role);  // 예외 처리
    }



}

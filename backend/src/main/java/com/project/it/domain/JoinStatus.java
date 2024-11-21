package com.project.it.domain;

public enum JoinStatus {
    WAITING("신규지원"),
    HOLD("보류"),
    DISMISSED("불합격"),
    PASSED("합격");  // PASSED에 큰따옴표를 추가

    private final String statusName;

    // 생성자 추가 (상태명을 저장)
    JoinStatus(String statusName) {
        this.statusName = statusName;
    }

    // 문자열을 enum 값으로 변환하는 메소드
    public static JoinStatus fromString(String status) {
        for (JoinStatus s : JoinStatus.values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown Role: " + status);
    }

    // toString()을 오버라이드하여 Enum 이름을 반환
    @Override
    public String toString() {
        return this.statusName; // 상태명을 반환
    }
}

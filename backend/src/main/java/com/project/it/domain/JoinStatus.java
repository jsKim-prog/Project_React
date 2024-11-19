package com.project.it.domain;

public enum JoinStatus {
    WAITING,
    HOLD,
    DISMISSED,
    PASSED,
    ;

    //method string -> enum
    public static JoinStatus fromString(String role){
        for (JoinStatus s : JoinStatus.values()){
            if(s.name().equalsIgnoreCase(role)){
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown Role : " + role);
    }
}

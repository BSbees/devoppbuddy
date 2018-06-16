package com.devopsbuddy.enums;

/**
 * Roles enum
 */
public enum PlansEnum {

    BASIC(1, "ROLE_BASIC"),
    PRO(2, "ROLE_PRO");

    private final int id;
    private final String roleName;

    PlansEnum(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}

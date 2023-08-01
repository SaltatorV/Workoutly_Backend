package com.workoutly.application.user.VO;

import java.util.Arrays;
import java.util.Set;

import static com.workoutly.application.user.VO.UserPermission.COMMON;

public enum UserRole {
    COMMON_USER("comonUser", Set.of(COMMON));

    private final String roleName;
    private final Set<UserPermission> permissions;

    UserRole(String roleName, Set<UserPermission> permissions) {
        this.roleName = roleName;
        this.permissions = permissions;
    }

    public static UserRole createRoleByName(String name) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.roleName.equals(name))
                .findFirst()
                .get();
    }

    public String getRoleName() {
        return roleName;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }
}

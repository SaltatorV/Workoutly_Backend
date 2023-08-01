package com.workoutly.application.user.VO;

public enum UserPermission {
  COMMON("user:common");

  private final String permission;

  UserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}

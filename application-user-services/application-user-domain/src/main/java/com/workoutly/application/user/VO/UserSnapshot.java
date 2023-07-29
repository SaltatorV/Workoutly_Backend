package com.workoutly.application.user.VO;

import java.util.Objects;

public class UserSnapshot {

  private final UserId userId;
  private final String username;
  private final String email;
  private final String password;
  private final UserRole role;
  private final boolean isEnabled;

  public UserSnapshot(UserId userId, String username, String email, String password, UserRole role, boolean isEnabled) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.isEnabled = isEnabled;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public UserRole getRole() {
    return role;
  }

  public String getEmail() {
    return email;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserSnapshot snapshot = (UserSnapshot) o;
    return isEnabled == snapshot.isEnabled && Objects.equals(userId, snapshot.userId) && Objects.equals(username, snapshot.username) && Objects.equals(email, snapshot.email) && Objects.equals(password, snapshot.password) && role == snapshot.role;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, username, email, password, role, isEnabled);
  }

  public static final class Builder {
    private UserId userId;
    private String username;
    private String email;
    private String password;
    private UserRole role;
    private boolean isEnabled;

    private Builder() {
    }

    public static Builder anUserSnapshot() {
      return new Builder();
    }

    public Builder withUserId(UserId userId) {
      this.userId = userId;
      return this;
    }

    public Builder withUsername(String username) {
      this.username = username;
      return this;
    }

    public Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder withRole(UserRole role) {
      this.role = role;
      return this;
    }

    public Builder withIsEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
      return this;
    }

    public UserSnapshot build() {
      return new UserSnapshot(userId, username, email, password, role, isEnabled);
    }
  }
}


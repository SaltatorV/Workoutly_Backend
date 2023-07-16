package com.workoutly.application.user.VO;

public class EmailAddress {
  private final String email;

  public EmailAddress(String email) {
    this.email = email;
  }

  public String getValue() {
    return email;
  }
}

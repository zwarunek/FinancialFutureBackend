package io.financialfuture.exceptions;

public enum ExceptionResponses {
  BAD_REQUEST("An error occurred: %s"), INCORRECT_AUTH(
      "Username or password was incorrect"), USERNAME_TAKEN(
      "Username is already in use"), NULL_VALUES("Null values present"), INVALID_TOKEN(
      "Invalid token"), EXPIRED_TOKEN("Invalid token"), EMAIL_ALREADY_CONFIRMED(
      "Email already confirmed"),

  ACCOUNT_NOT_FOUND("Account with id %s not found"), TOTAL_COMPENSATION_NOT_FOUND(
      "Account with id %s not found"), ACCOUNT_USERNAME_NOT_FOUND(
      "Account with username %s not found"), AUTH_FIELD_NOT_FOUND(
      "username or password field not found"), USERNAME_NOT_FOUND(
      "Account with username %s not found"), TOKEN_NOT_FOUND("token not found");

  public final String label;

  ExceptionResponses(String label) {
    this.label = label;
  }
}

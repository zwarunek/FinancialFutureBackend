package com.zacharywarunek.financialfuture.exceptions;

public class ExpiredTokenException extends Exception {
  public ExpiredTokenException(String message) {
    super(message);
  }
}

package com.ahmedmq.infonote.testcontainers.note;

public class UnauthorizedException extends RuntimeException{
  public UnauthorizedException(String permission_denied) {
    super(permission_denied);
  }
}

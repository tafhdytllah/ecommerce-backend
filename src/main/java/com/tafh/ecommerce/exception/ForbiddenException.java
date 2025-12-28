package com.tafh.ecommerce.exception;

public class ForbiddenException extends RuntimeException{

  public ForbiddenException(String message){
    super(message);
  }
}

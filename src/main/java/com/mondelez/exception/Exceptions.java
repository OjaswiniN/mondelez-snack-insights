/* (C) 2023 Mondelez Insights */
package com.mondelez.exception;

public class Exceptions {

  public static class InternalServerException extends RuntimeException {
    public InternalServerException() {
      super("Something Broken");
    }
  }

  public static class InvalidFileTypeException extends RuntimeException {
    public InvalidFileTypeException() {
      super("Not an sql file");
    }
  }

  public static class FileStorageException extends RuntimeException {
    public FileStorageException() {
      super("Failed to restore file");
    }
  }

  public static class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) {
      super(msg);
    }
  }

  public static class UnauthorizedUriException extends RuntimeException {
    public UnauthorizedUriException() {
      super(
          "Sorry! We've got an Unauthorized Redirect URI and can't proceed without the authentication");
    }
  }

  public static class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) {
      super(msg);
    }
  }
}

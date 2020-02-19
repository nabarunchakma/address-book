package au.com.chakma.addressbook.exceptions;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class AddressBookException extends Exception {
  public AddressBookException() {
  }

  public AddressBookException(String message) {
    super(message);
  }

  public AddressBookException(String message, Throwable cause) {
    super(message, cause);
  }

  public AddressBookException(Throwable cause) {
    super(cause);
  }

  public AddressBookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}

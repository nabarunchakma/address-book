package au.com.chakma.addressbook.caches;

import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public interface Cache {
  AddressBookEntity getAddressBook(String addressBookName) throws AddressBookException;
  void clear(String addressBookName);
}

package au.com.chakma.addressbook.caches;

import au.com.chakma.addressbook.dals.model.AddressBookEntity;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public interface Cache {
  AddressBookEntity getAddressBook(String addressBookName);
  void clear(String addressBookName);
}

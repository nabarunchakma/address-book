package au.com.chakma.addressbook.dals;

import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public interface AddressBookDal {
  void save(AddressBookEntity addressBookEntity) throws AddressBookException;
  AddressBookEntity load(String addressBookName) throws AddressBookException;
}

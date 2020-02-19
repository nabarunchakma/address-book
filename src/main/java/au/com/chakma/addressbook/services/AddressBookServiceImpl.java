package au.com.chakma.addressbook.services;

import au.com.chakma.addressbook.caches.Cache;
import au.com.chakma.addressbook.caches.InMemoryCache;
import au.com.chakma.addressbook.controllers.models.AddressBook;
import au.com.chakma.addressbook.controllers.models.Contact;
import au.com.chakma.addressbook.dals.AddressBookDal;
import au.com.chakma.addressbook.dals.AddressBookDalImpl;
import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;
import au.com.chakma.addressbook.helpers.AddressBookHelper;

import java.util.List;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class AddressBookServiceImpl implements AddressBookService {
  private Cache cache = InMemoryCache.getInstance();
  private AddressBookHelper addressBookHelper = new AddressBookHelper();
  private AddressBookDal addressBookDal = new AddressBookDalImpl();

  @Override
  public AddressBook loadAddressBook(final String addressBookName) {
    AddressBookEntity addressBookEntity = cache.getAddressBook(addressBookName);
    return addressBookHelper.convert(addressBookEntity);
  }

  @Override
  public AddressBook createAddressBook(final String addressBookName) throws AddressBookException {
    AddressBookEntity addressBookEntity = cache.getAddressBook(addressBookName);
    if (addressBookEntity == null) {
      addressBookEntity = new AddressBookEntity();
      addressBookEntity.setName(addressBookName);
      addressBookDal.save(addressBookEntity);
    }
    return addressBookHelper.convert(addressBookEntity);
  }

  @Override
  public AddressBook addContact(final String addressBookName, final Contact contact) throws AddressBookException {
    AddressBookEntity addressBookEntity = cache.getAddressBook(addressBookName);
    if (addressBookEntity != null && contact != null) {
      addressBookEntity.getContacts().add(addressBookHelper.convert(contact));
      addressBookDal.save(addressBookEntity);
    }
    return addressBookHelper.convert(addressBookEntity);
  }

  @Override
  public List<Contact> uniqueContacts(final String addressBookName, final String otherAddressBookName) {
    AddressBook addressBook = loadAddressBook(addressBookName);
    AddressBook otherAddressBook = loadAddressBook(otherAddressBookName);

    return addressBookHelper.getUniqueContacts(addressBook, otherAddressBook);
  }
}

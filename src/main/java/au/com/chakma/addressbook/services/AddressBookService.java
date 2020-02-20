package au.com.chakma.addressbook.services;

import au.com.chakma.addressbook.controllers.models.AddressBook;
import au.com.chakma.addressbook.controllers.models.Contact;
import au.com.chakma.addressbook.exceptions.AddressBookException;

import java.util.List;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public interface AddressBookService {
  AddressBook loadAddressBook(String addressBookName) throws AddressBookException;
  AddressBook createAddressBook(String addressBookName) throws AddressBookException;
  AddressBook addContact(String addressBookName, Contact contact) throws AddressBookException;
  List<Contact> uniqueContacts(String addressBookName, String otherAddressBook) throws AddressBookException;
}

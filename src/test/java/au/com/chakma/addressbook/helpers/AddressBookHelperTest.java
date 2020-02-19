package au.com.chakma.addressbook.helpers;

import au.com.chakma.addressbook.controllers.models.AddressBook;
import au.com.chakma.addressbook.controllers.models.Contact;
import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.dals.model.ContactEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class AddressBookHelperTest {
  AddressBookHelper addressBookHelper = new AddressBookHelper();

  @Test
  public void getUniqueContacts() {
    AddressBook addressBook = new AddressBook();
    Contact contact1 = createContact("John", "1");
    Contact contact2 = createContact("Harry", "2");
    Contact uniqueContact1 = createContact("John", "9");
    Contact uniqueContact2 = createContact("Sam", "8");
    addressBook.getContacts().addAll(Arrays.asList(contact1, uniqueContact1, contact2));

    AddressBook otherAddressBook = new AddressBook();
    otherAddressBook.getContacts().addAll(Arrays.asList(contact1, contact2, uniqueContact2));
    List<Contact> uniqueContacts = addressBookHelper.getUniqueContacts(addressBook, otherAddressBook);
    Assert.assertTrue(contains(uniqueContacts, uniqueContact1));
    Assert.assertTrue(contains(uniqueContacts, uniqueContact2));
    Assert.assertFalse(contains(uniqueContacts, contact1));
    Assert.assertFalse(contains(uniqueContacts, contact2));
  }

  @Test
  public void convertAddressBookEntity() {
    Assert.assertNull(addressBookHelper.convert((AddressBookEntity) null));
    Assert.assertNotNull(addressBookHelper.convert(new AddressBookEntity()));
  }

  @Test
  public void convertContactEntity() {
    Assert.assertNull(addressBookHelper.convert((ContactEntity) null));
    Assert.assertNotNull(addressBookHelper.convert(new ContactEntity()));
  }

  @Test
  public void convertContact() {
    Assert.assertNull(addressBookHelper.convert((Contact) null));
    Assert.assertNotNull(addressBookHelper.convert(new Contact()));
  }

  Contact createContact(final String name, final String telephone) {
    Contact contact = new Contact();
    contact.setName(name);
    contact.setTelephone(telephone);

    return contact;
  }

  boolean contains(final List<Contact> contacts, final Contact contact) {
    ContactComparator contactComparator = new ContactComparator();
    for(Contact contact1: contacts) {
      if (contactComparator.compare(contact1, contact) == 0) {
        return true;
      }
    }
    return false;
  }
}
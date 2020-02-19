package au.com.chakma.addressbook.helpers;

import au.com.chakma.addressbook.controllers.models.AddressBook;
import au.com.chakma.addressbook.controllers.models.Contact;
import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.dals.model.ContactEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class AddressBookHelper {
  private ContactComparator contactComparator = new ContactComparator();
  public List<Contact> getUniqueContacts(final AddressBook addressBook, final AddressBook otherAddressBook) {
    List<Contact> contacts = new ArrayList<>();
    for(Contact contact: addressBook.getContacts()) {
      boolean notFound = true;
      for(Contact otherContact: otherAddressBook.getContacts()) {
        if (contactComparator.compare(contact, otherContact) == 0) {
          notFound = false;
          break;
        }
      }
      if (notFound) {
        contacts.add(contact);
      }
    }

    for(Contact otherContact: otherAddressBook.getContacts()) {
      boolean notFound = true;
      for(Contact contact: addressBook.getContacts()) {
        if (contactComparator.compare(otherContact, contact) == 0) {
          notFound = false;
          break;
        }
      }

      if (notFound) {
        contacts.add(otherContact);
      }
    }
    return contacts;
  }

  public AddressBook convert(AddressBookEntity addressBookEntity) {
    if (addressBookEntity == null) {
      return null;
    }

    AddressBook addressBook = new AddressBook();
    addressBook.setName(addressBookEntity.getName());
    addressBookEntity.getContacts().forEach(contactEntity -> addressBook.getContacts().add(convert(contactEntity)));
    return addressBook;
  }

  public Contact convert(ContactEntity contactEntity) {
    if (contactEntity == null) {
      return null;
    }
    Contact contact = new Contact();
    contact.setId(contactEntity.getId());
    contact.setName(contactEntity.getName());
    contact.setTelephone(contactEntity.getTelephone());

    return contact;
  }

  public ContactEntity convert(Contact contact) {
    if (contact == null) {
      return null;
    }

    String id = contact.getId() == null || contact.getId().isEmpty() ? UUID.randomUUID().toString() : contact.getId();
    ContactEntity contactEntity = new ContactEntity();
    contactEntity.setId(id);
    contactEntity.setName(contact.getName());
    contactEntity.setTelephone(contact.getTelephone());

    return contactEntity;
  }
}

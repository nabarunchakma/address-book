package au.com.chakma.addressbook.services;

import au.com.chakma.addressbook.caches.Cache;
import au.com.chakma.addressbook.controllers.models.AddressBook;
import au.com.chakma.addressbook.controllers.models.Contact;
import au.com.chakma.addressbook.dals.AddressBookDal;
import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.dals.model.ContactEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;
import au.com.chakma.addressbook.helpers.AddressBookHelper;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.MockType;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
@RunWith(EasyMockRunner.class)
public class AddressBookServiceImplTest {
  @TestSubject
  private AddressBookService addressBookService = new AddressBookServiceImpl();

  @Mock(MockType.STRICT)
  private Cache cache;
  @Mock(MockType.STRICT)
  private AddressBookHelper addressBookHelper;
  @Mock(MockType.STRICT)
  private AddressBookDal addressBookDal;


  @Test
  public void loadAddressBook() {
    AddressBook addressBook = new AddressBook();
    AddressBookEntity addressBookEntity = new AddressBookEntity();
    EasyMock.expect(cache.getAddressBook("address-book")).andReturn(addressBookEntity);
    EasyMock.expect(addressBookHelper.convert(addressBookEntity)).andReturn(addressBook);
    EasyMock.replay(cache, addressBookHelper);
    Assert.assertEquals(addressBook, addressBookService.loadAddressBook("address-book"));
  }

  @Test
  public void createAddressBookWhenItDoesNotExist() throws AddressBookException {
    AddressBook addressBook = new AddressBook();
    EasyMock.expect(cache.getAddressBook("address-book")).andReturn(null);
    addressBookDal.save(EasyMock.anyObject(AddressBookEntity.class));
    EasyMock.expectLastCall();
    EasyMock.expect(addressBookHelper.convert(EasyMock.anyObject(AddressBookEntity.class))).andReturn(addressBook);
    EasyMock.replay(cache, addressBookDal, addressBookHelper);
    Assert.assertEquals(addressBook, addressBookService.createAddressBook("address-book"));
  }

  @Test
  public void createAddressBookWhenItExists() throws AddressBookException {
    AddressBook addressBook = new AddressBook();
    AddressBookEntity addressBookEntity = new AddressBookEntity();
    EasyMock.expect(cache.getAddressBook("address-book")).andReturn(addressBookEntity);
    EasyMock.expect(addressBookHelper.convert(addressBookEntity)).andReturn(addressBook);
    EasyMock.replay(cache, addressBookDal, addressBookHelper);
    Assert.assertEquals(addressBook, addressBookService.createAddressBook("address-book"));
  }

  @Test
  public void addContactWhenAddressBookIsNull() throws AddressBookException {
    Contact contact = new Contact();
    EasyMock.expect(cache.getAddressBook("address-book")).andReturn(null);
    EasyMock.expect(addressBookHelper.convert((AddressBookEntity) null)).andReturn(null);
    EasyMock.replay(cache, addressBookDal, addressBookHelper);
    Assert.assertNull(addressBookService.addContact("address-book", contact));
    EasyMock.verify(cache, addressBookDal, addressBookHelper);
  }

  @Test
  public void addContactWhenContactIsNull() throws AddressBookException {
    AddressBook addressBook = new AddressBook();
    AddressBookEntity addressBookEntity = new AddressBookEntity();
    EasyMock.expect(cache.getAddressBook("address-book")).andReturn(addressBookEntity);
    EasyMock.expect(addressBookHelper.convert(addressBookEntity)).andReturn(addressBook);
    EasyMock.replay(cache, addressBookDal, addressBookHelper);
    Assert.assertEquals(addressBookService.addContact("address-book", null), addressBook);
    EasyMock.verify(cache, addressBookDal, addressBookHelper);
  }


  @Test
  public void addContact() throws AddressBookException {
    AddressBook addressBook = new AddressBook();
    Contact contact = new Contact();
    ContactEntity contactEntity = new ContactEntity();
    AddressBookEntity addressBookEntity = new AddressBookEntity();
    EasyMock.expect(cache.getAddressBook("address-book")).andReturn(addressBookEntity);
    EasyMock.expect(addressBookHelper.convert(contact)).andReturn(contactEntity);
    EasyMock.expect(addressBookHelper.convert(addressBookEntity)).andReturn(addressBook);
    addressBookDal.save(addressBookEntity);
    EasyMock.expectLastCall();
    EasyMock.replay(cache, addressBookDal, addressBookHelper);
    Assert.assertEquals(addressBookService.addContact("address-book", contact), addressBook);
    EasyMock.verify(cache, addressBookDal, addressBookHelper);
  }

  @Test
  public void uniqueContacts() {
    List<Contact> contacts = new ArrayList<>();
    AddressBookEntity addressBookEntity1 = new AddressBookEntity();
    AddressBookEntity addressBookEntity2 = new AddressBookEntity();
    AddressBook addressBook1 = new AddressBook();
    AddressBook addressBook2 = new AddressBook();
    EasyMock.expect(cache.getAddressBook("address-book-1")).andReturn(addressBookEntity1);
    EasyMock.expect(cache.getAddressBook("address-book-2")).andReturn(addressBookEntity2);
    EasyMock.expect(addressBookHelper.convert(addressBookEntity1)).andReturn(addressBook1);
    EasyMock.expect(addressBookHelper.convert(addressBookEntity2)).andReturn(addressBook2);
    EasyMock.expect(addressBookHelper.getUniqueContacts(EasyMock.anyObject(AddressBook.class), EasyMock.anyObject(AddressBook.class))).andReturn(contacts);
    EasyMock.replay(cache, addressBookHelper, addressBookDal);
    Assert.assertEquals(contacts, addressBookService.uniqueContacts("address-book-1", "address-book-2"));
    EasyMock.verify(cache, addressBookHelper, addressBookDal);
  }
}
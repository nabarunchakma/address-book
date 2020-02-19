package au.com.chakma.addressbook.caches;

import au.com.chakma.addressbook.dals.AddressBookDal;
import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
@RunWith(EasyMockRunner.class)
public class InMemoryCacheTest {
  @TestSubject
  private InMemoryCache inMemoryCache = InMemoryCache.getInstance();

  @Mock
  private AddressBookDal addressBookDal;

  @Test
  public void getInstance() {
    Assert.assertNotNull("InMemoryCache should return an instance", InMemoryCache.getInstance());
  }

  @Test
  public void getAddressBook_When_Not_In_Cache() throws AddressBookException {
    AddressBookEntity addressBookEntity = new AddressBookEntity();
    EasyMock.expect(addressBookDal.load("name")).andReturn(addressBookEntity);
    EasyMock.replay(addressBookDal);
    Assert.assertEquals(addressBookEntity, inMemoryCache.getAddressBook("name"));
  }

  @Test
  public void getAddressBook_When_In_Cache() throws AddressBookException {
    AddressBookEntity firstAddressBook = new AddressBookEntity();
    AddressBookEntity secondAddressBook = new AddressBookEntity();
    EasyMock.expect(addressBookDal.load("name-1")).andReturn(firstAddressBook).andReturn(secondAddressBook);
    EasyMock.replay(addressBookDal);
    Assert.assertEquals(firstAddressBook, inMemoryCache.getAddressBook("name-1"));
    Assert.assertEquals(firstAddressBook, inMemoryCache.getAddressBook("name-1"));
  }

  @Test
  public void clear() throws AddressBookException {
    AddressBookEntity addressBookEntity = new AddressBookEntity();
    EasyMock.expect(addressBookDal.load("name-2")).andReturn(addressBookEntity).andReturn(null);
    EasyMock.replay(addressBookDal);
    Assert.assertEquals(addressBookEntity, inMemoryCache.getAddressBook("name-2"));
    inMemoryCache.clear("name-2");
    Assert.assertNull(inMemoryCache.getAddressBook("name-2"));
  }
}
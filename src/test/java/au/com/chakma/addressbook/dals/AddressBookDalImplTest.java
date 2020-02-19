package au.com.chakma.addressbook.dals;

import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.dals.model.ContactEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;
import au.com.chakma.addressbook.helpers.FileStore;
import com.google.gson.Gson;
import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
@RunWith(EasyMockRunner.class)
public class AddressBookDalImplTest {
  @TestSubject
  private AddressBookDal addressBookDal = new AddressBookDalImpl();
  @Mock
  private FileStore fileStore;
  private AddressBookEntity addressBookEntity;

  @Before
  public void setUp() {
    addressBookEntity = new AddressBookEntity();
    addressBookEntity.setName("address-book");
    addressBookEntity.getContacts().add(new ContactEntity());
  }

  @Test
  public void save() throws AddressBookException {
    fileStore.save(EasyMock.eq("address-book"), EasyMock.anyString());
    EasyMock.expectLastCall();
    EasyMock.replay(fileStore);
    addressBookDal.save(addressBookEntity);
    EasyMock.verify(fileStore);
  }

  @Test
  public void load() throws AddressBookException {
    Gson gson = new Gson();
    String serializedAddressBook = gson.toJson(addressBookEntity);
    EasyMock.expect(fileStore.read(EasyMock.anyString())).andReturn(serializedAddressBook);
    EasyMock.replay(fileStore);
    AddressBookEntity retrievedAddressBook = addressBookDal.load(addressBookEntity.getName());
    Assert.assertEquals(addressBookEntity.getName(), retrievedAddressBook.getName());
  }
}
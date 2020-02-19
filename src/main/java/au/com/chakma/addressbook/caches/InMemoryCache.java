package au.com.chakma.addressbook.caches;

import au.com.chakma.addressbook.dals.AddressBookDal;
import au.com.chakma.addressbook.dals.AddressBookDalImpl;
import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public class InMemoryCache implements Cache {
  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryCache.class);
  private static Map<String, AddressBookEntity> addressBooks = new HashMap<>();
  private static InMemoryCache inMemoryCache;
  private AddressBookDal addressBookDal = new AddressBookDalImpl();

  private InMemoryCache() {

  }

  public static InMemoryCache getInstance() {
    if (inMemoryCache == null) {
      inMemoryCache = new InMemoryCache();
    }

    return inMemoryCache;
  }

  @Override
  public AddressBookEntity getAddressBook(String addressBookName) {
    if (!addressBooks.containsKey(addressBookName)) {
      AddressBookEntity addressBookEntity = null;
      try {
        addressBookEntity = addressBookDal.load(addressBookName);
        if (addressBookEntity != null) {
          addressBooks.put(addressBookName, addressBookEntity);
        }
      } catch (AddressBookException e) {
        LOGGER.debug("Problem with the Address Book", e);
      }
    }
    return addressBooks.get(addressBookName);
  }

  @Override
  public void clear(String addressBookName) {
    addressBooks.remove(addressBookName);
  }
}

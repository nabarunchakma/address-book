package au.com.chakma.addressbook.dals;

import au.com.chakma.addressbook.dals.model.AddressBookEntity;
import au.com.chakma.addressbook.exceptions.AddressBookException;
import au.com.chakma.addressbook.helpers.FileStore;
import com.google.gson.Gson;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class AddressBookDalImpl implements AddressBookDal {
  private FileStore fileStore = new FileStore();
  private Gson gson = new Gson();

  @Override
  public void save(final AddressBookEntity addressBookEntity) throws AddressBookException {
    fileStore.save(addressBookEntity.getName(), gson.toJson(addressBookEntity));
  }

  @Override
  public AddressBookEntity load(final String addressBookName) throws AddressBookException {
    String addressBook = fileStore.read(addressBookName);
    return gson.fromJson(addressBook, AddressBookEntity.class);
  }
}

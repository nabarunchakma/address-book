package au.com.chakma.addressbook.dals.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public class AddressBookEntity {
  private String name;
  private List<ContactEntity> contacts = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<ContactEntity> getContacts() {
    return contacts;
  }

  public void setContacts(List<ContactEntity> contacts) {
    this.contacts = contacts;
  }
}

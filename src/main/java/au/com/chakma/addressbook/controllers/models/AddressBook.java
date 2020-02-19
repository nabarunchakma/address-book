package au.com.chakma.addressbook.controllers.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class AddressBook {
  private String name;
  private List<Contact> contacts = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }
}

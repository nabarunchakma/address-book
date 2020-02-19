package au.com.chakma.addressbook.helpers;

import au.com.chakma.addressbook.controllers.models.Contact;

import java.util.Comparator;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class ContactComparator implements Comparator<Contact> {
  @Override
  public int compare(Contact o1, Contact o2) {
    if (o1 != null && o2 != null) {
      int nameComparison = o1.getName().compareToIgnoreCase(o2.getName());
      return nameComparison == 0 ? o1.getTelephone().compareToIgnoreCase(o2.getTelephone()) : nameComparison;
    }
    return 0;
  }
}

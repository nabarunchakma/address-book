package au.com.chakma.addressbook.helpers;

import au.com.chakma.addressbook.controllers.models.Contact;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class ContactComparatorTest {
  private ContactComparator contactComparator = new ContactComparator();

  @Test
  public void compareWhenContactsAreNull() {
    Assert.assertEquals(0, contactComparator.compare(null, null));
    Assert.assertEquals(0, contactComparator.compare(new Contact(), null));
    Assert.assertEquals(0, contactComparator.compare(null, new Contact()));
  }

  @Test
  public void compareWhenContactNamesAreNotSame() {
    Contact contact1 = new Contact();
    contact1.setName("John");
    contact1.setTelephone("00000000");
    Contact contact2 = new Contact();
    contact2.setName("Johnson");
    contact2.setTelephone("00000000");
    Assert.assertTrue(contactComparator.compare(contact1, contact2) < 0);
  }

  @Test
  public void compareWhenContactNamesAreSame() {
    Contact contact1 = new Contact();
    contact1.setName("John");
    contact1.setTelephone("00000000");
    Contact contact2 = new Contact();
    contact2.setName("John");
    contact2.setTelephone("11111111");
    Assert.assertTrue(contactComparator.compare(contact1, contact2) < 0);
  }

  @Test
  public void compare() {
    Contact contact1 = new Contact();
    contact1.setName("John");
    contact1.setTelephone("00000000");
    Contact contact2 = new Contact();
    contact2.setName("John");
    contact2.setTelephone("00000000");
    Assert.assertTrue(contactComparator.compare(contact1, contact2) == 0);
  }
}
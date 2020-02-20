package au.com.chakma.addressbook.controllers;

import au.com.chakma.addressbook.controllers.models.AddressBook;
import au.com.chakma.addressbook.controllers.models.Contact;
import au.com.chakma.addressbook.exceptions.AddressBookException;
import au.com.chakma.addressbook.services.AddressBookService;
import au.com.chakma.addressbook.services.AddressBookServiceImpl;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by nabarunchakma on 2020-02-20.
 */
@Command(name = "address-book", mixinStandardHelpOptions = true, version = "1.0",
    description = "Manage address books")
public class AddressBookController implements Callable<Integer> {
  private enum Action {
    create, view, add, diff
  }
  private AddressBookService addressBookService = new AddressBookServiceImpl();
  @Parameters(index = "0", description = "The action on a address book. i.e. create, view, add, diff")
  private Action action;
  @Option(names = {"-n", "--name"}, description = "The name of the address book")
  private String addressBookName;
  @Option(names = {"-o", "--other-name"}, description = "The name of the other address book")
  private String otherAddressBookName;
  @Option(names = {"-c", "--contact-name"}, description = "The name of a contact")
  private String contactName;
  @Option(names = {"-t", "--telephone"}, description = "The phone number of a contact")
  private String telephone;

  public static void main(String... args) {
    int exitCode = new CommandLine(new AddressBookController()).execute(args);
    System.exit(exitCode);
  }
  
  @Override
  public Integer call() throws Exception {
    if (validateArguments() != 0) {
      return 0;
    }

    switch (action) {
      case create:
        createAddressBook(addressBookName);
        break;
      case add:
        addContactToAddressBook(addressBookName, contactName, telephone);
        break;
      case view:
        viewAddressBook(addressBookName);
        break;
      case diff:
        showDiffAddressBooks(addressBookName, otherAddressBookName);
        break;
      default:
        break;
    }
    return 0;
  }

  int validateArguments() {
    switch (action) {
      case create:
      case view:
        return validateCreateOrViewCommand();
      case add:
        return validateAddCommand();
      case diff:
        return validateDiffCommand();
    }

    return 0;
  }

  int validateCreateOrViewCommand() {
    if (addressBookName == null) {
      System.out.println("addressBookName is required");
      return 1;
    }
    return 0;
  }

  int validateAddCommand() {
    if (addressBookName == null) {
      System.out.println("addressBookName is required");
      return 1;
    }

    if (contactName == null) {
      System.out.println("contactName is required");
      return 1;
    }

    if (telephone == null) {
      System.out.println("telephone is required");
      return 1;
    }

    return 0;
  }

  int validateDiffCommand() {
    if (addressBookName == null) {
      System.out.println("addressBookName is required");
      return 1;
    }

    if (otherAddressBookName == null) {
      System.out.println("otherAddressBookName is required");
      return 1;
    }
    return 0;
  }

  void createAddressBook(final String addressBookName) {
    try {
      addressBookService.createAddressBook(addressBookName);
      String message = String.format("Address Book <%s> created successfully.", addressBookName);
      System.out.println(message);
    } catch (AddressBookException e) {
      System.out.println("Failed to create the address book");
      e.printStackTrace();
    }
  }

  void addContactToAddressBook(final String addressBookName, final String name, final String telephone) {
    Contact contact = new Contact();
    contact.setName(name);
    contact.setTelephone(telephone);
    try {
      addressBookService.addContact(addressBookName, contact);
      String message = String.format("Contact <%s> added to Address Book <%s>.", name, addressBookName);
      System.out.println(message);
    } catch (AddressBookException e) {
      System.out.println("Failed to add contact in the address book");
      e.printStackTrace();
    }
  }

  void viewAddressBook(final String addressBookName) {
    try {
      AddressBook addressBook = addressBookService.loadAddressBook(addressBookName);
      System.out.println(addressBook.toString());
    } catch (AddressBookException e) {
      System.out.println("Address book is not found");
      e.printStackTrace();
    }
  }

  void showDiffAddressBooks(final String addressBookName, final String otherAddressBookName) {
    try {
      List<Contact> contacts = addressBookService.uniqueContacts(addressBookName, otherAddressBookName);
      System.out.println("The Unique Contacts");
      System.out.println("--------------------");
      contacts.forEach(contact -> System.out.println(contact.toString()));
    } catch (AddressBookException e) {
      System.out.println("One or both Address books not found");
      e.printStackTrace();
    }
  }
}

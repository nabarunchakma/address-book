package au.com.chakma.addressbook.helpers;

import au.com.chakma.addressbook.exceptions.AddressBookException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public class FileStore implements Store {
  private final static Logger LOGGER = LoggerFactory.getLogger(FileStore.class);
  @Override
  public void save(final String name, final String content) throws AddressBookException {

    try {
      BufferedWriter writer = getBufferedWriterInstance(getFileName(name));
      writer.write(content);
      writer.close();
    } catch (IOException e) {
      LOGGER.error("Error saving the address book {}", name);
      throw new AddressBookException(e);
    }
  }

  @Override
  public String read(final String name) throws AddressBookException {
    StringBuilder contentBuilder = new StringBuilder();
    try {
      BufferedReader br = getBufferedReaderInstance(getFileName(name));
      String line;
      while ((line = br.readLine()) != null) {
        contentBuilder.append(line);
      }
      br.close();
    }
    catch (IOException e) {
      LOGGER.error("Error reading the address book {}", name);
      throw new AddressBookException(e);
    }
    return contentBuilder.toString();
  }

  BufferedWriter getBufferedWriterInstance(final String fileName) throws IOException {
    return new BufferedWriter(new FileWriter(fileName));
  }

  BufferedReader getBufferedReaderInstance(final String fileName) throws FileNotFoundException {
    return new BufferedReader(new FileReader(fileName));
  }

  String getFileName(final String name) {
    if (name == null || name.isEmpty()) {
      return "address-book.json";
    }

    String sanitizedName = name.replace(" ", "-").toLowerCase();
    return String.format("%s.json", sanitizedName);
  }
}

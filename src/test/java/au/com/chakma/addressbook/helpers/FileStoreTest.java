package au.com.chakma.addressbook.helpers;

import au.com.chakma.addressbook.exceptions.AddressBookException;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by nabarunchakma on 2020-02-19.
 */
public class FileStoreTest {
  FileStore fileStore = new FileStore();

  @Test(expected = AddressBookException.class)
  public void saveWhenFileWriterThrowsIOException() throws Exception {
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedWriterInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedWriterInstance("filename.json")).andThrow(new IOException());
    EasyMock.replay(fileStoreMock);
    fileStoreMock.save("filename", "some content");
  }

  @Test(expected = AddressBookException.class)
  public void saveWhenWriterWriteThrowsIOException() throws Exception {
    BufferedWriter bufferedWriterMock = EasyMock.mock(BufferedWriter.class);
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedWriterInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedWriterInstance("filename.json")).andReturn(bufferedWriterMock);
    bufferedWriterMock.write(EasyMock.anyString());
    EasyMock.expectLastCall().andThrow(new IOException());
    EasyMock.replay(fileStoreMock, bufferedWriterMock);
    fileStoreMock.save("filename", "some content");
    EasyMock.verify(fileStoreMock, bufferedWriterMock);
  }

  @Test(expected = AddressBookException.class)
  public void saveWhenWriterCloseThrowsIOException() throws Exception {
    BufferedWriter bufferedWriterMock = EasyMock.mock(BufferedWriter.class);
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedWriterInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedWriterInstance("filename.json")).andReturn(bufferedWriterMock);
    bufferedWriterMock.write(EasyMock.anyString());
    EasyMock.expectLastCall();
    bufferedWriterMock.close();
    EasyMock.expectLastCall().andThrow(new IOException());
    EasyMock.replay(fileStoreMock, bufferedWriterMock);
    fileStoreMock.save("filename", "some content");
    EasyMock.verify(fileStoreMock, bufferedWriterMock);
  }

  @Test
  public void save() throws Exception {
    BufferedWriter bufferedWriterMock = EasyMock.mock(BufferedWriter.class);
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedWriterInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedWriterInstance("filename.json")).andReturn(bufferedWriterMock);
    bufferedWriterMock.write("some content");
    EasyMock.expectLastCall();
    bufferedWriterMock.close();
    EasyMock.expectLastCall();
    EasyMock.replay(fileStoreMock, bufferedWriterMock);
    fileStoreMock.save("filename", "some content");
    EasyMock.verify(fileStoreMock, bufferedWriterMock);
  }

  @Test(expected = AddressBookException.class)
  public void readWhenFileReaderThrowsFileNotFoundException() throws Exception {
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedReaderInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedReaderInstance("filename.json")).andThrow(new FileNotFoundException());
    EasyMock.replay(fileStoreMock);
    fileStoreMock.read("filename");
    EasyMock.verify(fileStoreMock);
  }

  @Test(expected = AddressBookException.class)
  public void readWhenReaderReadlineThrowsIOException() throws IOException, AddressBookException {
    BufferedReader bufferedReaderMock = EasyMock.mock(BufferedReader.class);
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedReaderInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedReaderInstance("filename.json")).andReturn(bufferedReaderMock);
    EasyMock.expect(bufferedReaderMock.readLine()).andThrow(new IOException());
    EasyMock.replay(fileStoreMock, bufferedReaderMock);
    fileStoreMock.read("filename");
    EasyMock.verify(fileStoreMock, bufferedReaderMock);
  }

  @Test(expected = AddressBookException.class)
  public void readWhenReaderCloseThrowsIOException() throws IOException, AddressBookException {
    BufferedReader bufferedReaderMock = EasyMock.mock(BufferedReader.class);
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedReaderInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedReaderInstance("filename.json")).andReturn(bufferedReaderMock);
    EasyMock.expect(bufferedReaderMock.readLine()).andReturn("test").andReturn(null);
    bufferedReaderMock.close();
    EasyMock.expectLastCall().andThrow(new IOException());
    EasyMock.replay(fileStoreMock, bufferedReaderMock);
    fileStoreMock.read("filename");
    EasyMock.verify(fileStoreMock, bufferedReaderMock);
  }

  @Test
  public void read() throws IOException, AddressBookException {
    BufferedReader bufferedReaderMock = EasyMock.mock(BufferedReader.class);
    FileStore fileStoreMock = EasyMock.partialMockBuilder(FileStore.class)
        .addMockedMethod("getBufferedReaderInstance")
        .createMock();
    EasyMock.expect(fileStoreMock.getBufferedReaderInstance("filename.json")).andReturn(bufferedReaderMock);
    EasyMock.expect(bufferedReaderMock.readLine()).andReturn("test").andReturn(null);
    bufferedReaderMock.close();
    EasyMock.expectLastCall();
    EasyMock.replay(fileStoreMock, bufferedReaderMock);
    fileStoreMock.read("filename");
    EasyMock.verify(fileStoreMock, bufferedReaderMock);
  }

  @Test
  public void getFileName() {
    Assert.assertEquals("address-book.json", fileStore.getFileName(null));
    Assert.assertEquals("address-book.json", fileStore.getFileName(""));
    Assert.assertEquals("test.json", fileStore.getFileName("Test"));
  }
}
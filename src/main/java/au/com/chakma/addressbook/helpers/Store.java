package au.com.chakma.addressbook.helpers;

/**
 * Created by nabarunchakma on 2020-02-18.
 */
public interface Store {
  void save(String name, String content) throws Exception;
  String read(String name) throws Exception;
}

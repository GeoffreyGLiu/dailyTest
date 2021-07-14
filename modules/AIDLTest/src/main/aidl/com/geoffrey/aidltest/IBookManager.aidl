// IBookManager.aidl
package com.geoffrey.aidltest;
import com.geoffrey.aidltest.Book;
import java.util.List;

interface IBookManager {

    Book getBookByIndex(in int index);

    void addBookToList(in Book book);

    List<Book> getBookList();
}

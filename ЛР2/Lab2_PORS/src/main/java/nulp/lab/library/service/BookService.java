package nulp.lab.library.service;

import nulp.lab.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger();

    public List<Book> getAllBooks() {
        return books;
    }

    public Book addBook(Book book) {
        book.setId(idGenerator.incrementAndGet());
        books.add(book);
        return book;
    }

    public boolean updateBook(int id, Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                updatedBook.setId(id);
                books.set(i, updatedBook);
                return true;
            }
        }
        return false;
    }

    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getId() == id);
    }
}

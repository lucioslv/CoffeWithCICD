package br.com.project.restwithspringboot.utils.mocks;

import br.com.project.restwithspringboot.domain.models.Book;
import br.com.project.restwithspringboot.domain.dtos.v1.BookDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MockBook {

    public Book mockEntity() {
        return mockEntity(0);
    }

    public BookDto mockVO() {
        return mockVO(0);
    }

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDto> mockVOList() {
        List<BookDto> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockVO(i));
        }
        return books;
    }

    private Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setAuthor("Michael C. Feathers" + number);
        book.setLaunchDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setPrice(new BigDecimal("49.00"));
        book.setTitle("Working effectively with legacy code" + number);
        return book;
    }

    private BookDto mockVO(Integer number) {
        BookDto book = new BookDto();
        book.setKey(number.longValue());
        book.setAuthor("Michael C. Feathers" + number);
        book.setLaunchDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        book.setPrice(new BigDecimal("49.00"));
        book.setTitle("Working effectively with legacy code" + number);
        return book;
    }
}

package br.com.project.restwithspringboot.services;

import br.com.project.restwithspringboot.domain.models.Book;
import br.com.project.restwithspringboot.domain.dtos.v1.BookDto;
import br.com.project.restwithspringboot.exceptions.ResourceNotFoundException;
import br.com.project.restwithspringboot.repositories.BookRepository;
import br.com.project.restwithspringboot.utils.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServices {

    @Autowired
    BookRepository repository;

    public BookDto findById(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        return DozerConverter.parseObject(entity, BookDto.class);
    }

    public Page<BookDto> findAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return page.map(this::convertToBookDto);
    }

    private BookDto convertToBookDto(Book entity){
        return DozerConverter.parseObject(entity, BookDto.class);
    }

    public BookDto create(BookDto book){
        var entity= DozerConverter.parseObject(book, Book.class);
        return DozerConverter.parseObject(repository.save(entity), BookDto.class);
    }

    public BookDto update(BookDto book){
        var entity = repository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
        return DozerConverter.parseObject(repository.save(entity), BookDto.class);
    }

    public void delete(Long id){
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        repository.delete(entity);
    }
}

package br.com.project.restwithspringboot.controllers;

import br.com.project.restwithspringboot.domain.dtos.v1.BookDto;
import br.com.project.restwithspringboot.services.BookServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(tags = {"Book Endpoint"})
@RestController
@RequestMapping("/api/book/v1")
public class BookController {

    @Autowired
    private BookServices service;

    @Autowired
    private PagedResourcesAssembler<BookDto> assembler;

    @ApiOperation("Find all books")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "10") int limit,
            @RequestParam(value="direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

        Page<BookDto> books =  service.findAll(pageable);

        books.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

        PagedModel<?> resources = assembler.toModel(books);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Find a specific book by your ID")
    @GetMapping("/{id}")
    public BookDto findById(@PathVariable(value="id") Long id) {
        BookDto bookDto = service.findById(id);
        bookDto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return bookDto;
    }

    @ApiOperation("Create a new book")
    @PostMapping
    public BookDto create(@RequestBody BookDto book) {
        BookDto bookDto = service.create(book);
        bookDto.add(linkTo(methodOn(BookController.class).findById(bookDto.getKey())).withSelfRel());
        return bookDto;
    }

    @ApiOperation("Update a specific book")
    @PutMapping
    public BookDto update(@RequestBody BookDto book) {
        BookDto bookDto = service.update(book);
        bookDto.add(linkTo(methodOn(BookController.class).findById(bookDto.getKey())).withSelfRel());
        return bookDto;
    }

    @ApiOperation("Delete a specific book by your ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

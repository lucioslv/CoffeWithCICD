package br.com.project.restwithspringboot.controllers;

import br.com.project.restwithspringboot.domain.dtos.v1.PersonDto;
import br.com.project.restwithspringboot.services.PersonServices;
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

@Api(tags = {"Person Endpoint"})
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

    @Autowired
    private PersonServices service;

    @Autowired
    private PagedResourcesAssembler<PersonDto> assembler;

    @ApiOperation("Find all people with token name")
    @GetMapping("/findPersonByName/{firstName}")
    public ResponseEntity<?> findPersonByName(
            @PathVariable(value="firstName") String firstName,
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "10") int limit,
            @RequestParam(value="direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        Page<PersonDto> persons =  service.findPersonByName(firstName, pageable);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        PagedModel<?> resources = assembler.toModel(persons);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Find all persons")
    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value="page", defaultValue = "0") int page,
            @RequestParam(value="limit", defaultValue = "10") int limit,
            @RequestParam(value="direction", defaultValue = "asc") String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        Page<PersonDto> persons =  service.findAll(pageable);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        PagedModel<?> resources = assembler.toModel(persons);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @ApiOperation("Find a specific person by your ID")
    @GetMapping("/{id}")
    public PersonDto findById(@PathVariable(value="id") Long id) {
        PersonDto personDto = service.findById(id);
        personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personDto;
    }

    @ApiOperation("Create a new person")
    @PostMapping
    public PersonDto create(@RequestBody PersonDto person) {
        PersonDto personDto = service.create(person);
        personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getKey())).withSelfRel());
        return personDto;
    }

    @ApiOperation("Update a specific person")
    @PutMapping
    public PersonDto update(@RequestBody PersonDto person) {
        PersonDto personDto = service.update(person);
        personDto.add(linkTo(methodOn(PersonController.class).findById(personDto.getKey())).withSelfRel());
        return personDto;
    }

    @ApiOperation("Disable a specific person by your ID")
    @PatchMapping("/{id}")
    public PersonDto disablePerson(@PathVariable(value="id") Long id) {
        PersonDto personDto = service.disablePerson(id);
        personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personDto;
    }

    @ApiOperation("Delete a specific person by your ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value="id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}

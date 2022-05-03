package br.com.project.restwithspringboot.services;

import br.com.project.restwithspringboot.domain.dtos.v1.PersonDto;
import br.com.project.restwithspringboot.exceptions.ResourceNotFoundException;
import br.com.project.restwithspringboot.domain.models.Person;
import br.com.project.restwithspringboot.repositories.PersonRepository;
import br.com.project.restwithspringboot.utils.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PersonServices {

    @Autowired
    PersonRepository repository;

    public PersonDto findById(Long id){
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        return DozerConverter.parseObject(entity, PersonDto.class);
    }

    public Page<PersonDto> findPersonByName(String firstName, Pageable pageable) {
        var page = repository.findPersonByName(firstName, pageable);
        return page.map(this::convertToPersonDto);
    }

    public Page<PersonDto> findAll(Pageable pageable) {
        var page = repository.findAll(pageable);
        return page.map(this::convertToPersonDto);
    }

    private PersonDto convertToPersonDto(Person entity){
        return DozerConverter.parseObject(entity, PersonDto.class);
    }

    public PersonDto create(PersonDto person){
        var entity= DozerConverter.parseObject(person, Person.class);
        return DozerConverter.parseObject(repository.save(entity), PersonDto.class);
    }

    public PersonDto update(PersonDto person){
        var entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return DozerConverter.parseObject(repository.save(entity), PersonDto.class);
    }

    @Transactional
    public PersonDto disablePerson(Long id){
        repository.disablePersons(id);
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        return DozerConverter.parseObject(entity, PersonDto.class);
    }

    public void delete(Long id){
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID."));
        repository.delete(entity);
    }
}

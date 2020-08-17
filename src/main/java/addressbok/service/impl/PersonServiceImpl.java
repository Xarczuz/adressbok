package addressbok.service.impl;

import addressbok.model.Person;
import addressbok.repo.PersonRepo;
import addressbok.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepo personRepo;

    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Person addPerson(Person person) {
        return personRepo.save(person);
    }

    @Override
    public List<Person> getAllPerson() {
        return personRepo.findAll();
    }

    @Override
    public Person getPerson(int id) throws NoSuchElementException {
        return personRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void deletePerson(int id) {
        personRepo.deleteById(id);
    }

    @Override
    public Person updatePerson(Person person) throws NoSuchElementException {

        Person person1 = personRepo.findById(person.getId()).orElseThrow(NoSuchElementException::new);
        person1.setFirstname(person.getFirstname());
        person1.setLastname(person.getLastname());
        person1.setEmail(person.getEmail());
        person1.setPhonenr(person.getPhonenr());
        return personRepo.save(person1);

    }
}

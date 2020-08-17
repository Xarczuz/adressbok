package addressbok.service;

import addressbok.model.Person;

import java.util.List;
import java.util.NoSuchElementException;

public interface PersonService {
    Person createPerson(Person person);

    List<Person> getAllPerson();

    Person getPerson(int id) throws NoSuchElementException;

    void deletePerson(int id);

    Person updatePerson(Person person) throws NoSuchElementException;
}

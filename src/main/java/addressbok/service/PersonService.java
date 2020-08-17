package addressbok.service;

import addressbok.model.Person;

import java.util.List;

public interface PersonService {
    Person addPerson(Person person);

    List<Person> getAllPerson();

    Person getPerson(int id) throws Exception;

    void deletePerson(int id);

    Person updatePerson(Person person) throws Exception;
}

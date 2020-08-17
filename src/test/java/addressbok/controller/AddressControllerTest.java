package addressbok.controller;

import addressbok.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllPersons() throws Exception {
        String expected = "[{\"id\":1,\"firstname\":\"Karl\",\"lastname\":\"Wii\",\"email\":\"Karl@gmail.com\",\"phonenr\":\"234-3463527\",\"country\":\"Sweden\",\"address\":\"Arsta skolgränd 12B 117 43 Stockholm\"},{\"id\":2,\"firstname\":\"James\",\"lastname\":\"KI\",\"email\":\"James@gmail.com\",\"phonenr\":\"24-2353485\",\"country\":\"USA\",\"address\":\"9462 Brenda Ave Affton, MO 63123\"},{\"id\":3,\"firstname\":\"Sam\",\"lastname\":\"Django\",\"email\":\"Sam@gmail.com\",\"phonenr\":\"34-85623524\",\"country\":\"Finland\",\"address\":\"Keskusta 40100 Jyväskylä\"}";

        mockMvc.perform(get("/address")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));

    }

    @Test
    void getPerson() throws Exception {
        String expected = "{\"id\":1,\"firstname\":\"Karl\",\"lastname\":\"Wii\",\"email\":\"Karl@gmail.com\",\"phonenr\":\"234-3463527\",\"country\":\"Sweden\",\"address\":\"Arsta skolgränd 12B 117 43 Stockholm\"}";

        mockMvc.perform(get("/address/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));

    }

    @Test
    void getPersonFailTest() throws Exception {
        mockMvc.perform(get("/address/85")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    void getPersonFailTest2() throws Exception {
        mockMvc.perform(get("/address/g")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void createPerson() throws Exception {
        String expected = "{\"id\":4,\"firstname\":\"Par\",\"lastname\":\"Holmstrom\",\"email\":\"pear@gmail.com\",\"phonenr\":\"07-5851213\",\"country\":null,\"address\":null}";
        Person person = new Person();
        person.setFirstname("Par");
        person.setLastname("Holmstrom");
        person.setEmail("pear@gmail.com");
        person.setPhonenr("07-5851213");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);

        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(expected)));

    }

    @Test
    void addPersonFailTest() throws Exception {
        Person person = new Person();
        person.setFirstname("Par");
        //Missing last name
        person.setEmail("pear@gmail.com");
        person.setPhonenr("07-5851213");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);

        mockMvc.perform(post("/address")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void updatePerson() throws Exception {
        String expected = "{\"id\":1,\"firstname\":\"Par\",\"lastname\":\"Holmstrom\",\"email\":\"pear@gmail.com\",\"phonenr\":\"07-5851213\",\"country\":\"Sweden\",\"address\":\"Arsta skolgränd 12B 117 43 Stockholm\"}";
        Person person = new Person();
        person.setId(1);
        person.setFirstname("Par");
        person.setLastname("Holmstrom");
        person.setEmail("pear@gmail.com");
        person.setPhonenr("07-5851213");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);

        mockMvc.perform(put("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expected)));

    }

    @Test
    void updatePersonFailTest() throws Exception {
        Person person = new Person();
        person.setId(53);//No such id
        person.setFirstname("Par");
        person.setLastname("Holmstrom");
        person.setEmail("pear@gmail.com");
        person.setPhonenr("07-5851213");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);

        mockMvc.perform(put("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void deletePerson() throws Exception {
        String expected = "[{\"id\":2,\"firstname\":\"James\",\"lastname\":\"KI\",\"email\":\"James@gmail.com\",\"phonenr\":\"24-2353485\",\"country\":\"USA\",\"address\":\"9462 Brenda Ave Affton, MO 63123\"},{\"id\":3,\"firstname\":\"Sam\",\"lastname\":\"Django\",\"email\":\"Sam@gmail.com\",\"phonenr\":\"34-85623524\",\"country\":\"Finland\",\"address\":\"Keskusta 40100 Jyväskylä\"}]";

        mockMvc.perform(delete("/address/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/address")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expected)))
                .andExpect(status().isOk());

    }

    @Test
    void deletePersonFailTest() throws Exception {
        mockMvc.perform(delete("/address/155")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}

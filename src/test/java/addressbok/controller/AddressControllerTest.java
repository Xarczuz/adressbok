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

import static org.junit.jupiter.api.Assertions.*;
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
        String expected = "[{\"id\":1,\"firstname\":\"Karl\",\"lastname\":\"Wii\",\"email\":\"Karl@gmail.com\",\"phonenr\":\"234-3463527\"},{\"id\":2,\"firstname\":\"James\",\"lastname\":\"KI\",\"email\":\"James@gmail.com\",\"phonenr\":\"24-2353485\"},{\"id\":3,\"firstname\":\"Sam\",\"lastname\":\"Django\",\"email\":\"Sam@gmail.com\",\"phonenr\":\"34-85623524\"}]";
        //Get
        String response = mockMvc.perform(get("/address")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, response);

    }

    @Test
    void getPerson() throws Exception {
        String expected = "{\"id\":1,\"firstname\":\"Karl\",\"lastname\":\"Wii\",\"email\":\"Karl@gmail.com\",\"phonenr\":\"234-3463527\"}";
        //Get
        String response = mockMvc.perform(get("/address/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, response);

    }

    @Test
    void getPersonFailTest() throws Exception {
        //Get
        mockMvc.perform(get("/address/85")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getPersonFailTest2() throws Exception {
        //Get
        mockMvc.perform(get("/address/g")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addPerson() throws Exception {
        String expected = "{\"id\":4,\"firstname\":\"Par\",\"lastname\":\"Holmstrom\",\"email\":\"pear@gmail.com\",\"phonenr\":\"07-5851213\"}";
        Person person = new Person();
        person.setFirstname("Par");
        person.setLastname("Holmstrom");
        person.setEmail("pear@gmail.com");
        person.setPhonenr("07-5851213");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);
        //Post
        String response = mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, response);

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
        //Post
        mockMvc.perform(post("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void updatePerson() throws Exception {
        String expected = "{\"id\":1,\"firstname\":\"Par\",\"lastname\":\"Holmstrom\",\"email\":\"pear@gmail.com\",\"phonenr\":\"07-5851213\"}";
        Person person = new Person();
        person.setId(1);
        person.setFirstname("Par");
        person.setLastname("Holmstrom");
        person.setEmail("pear@gmail.com");
        person.setPhonenr("07-5851213");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);
        //Put
        String response = mockMvc.perform(put("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, response);

    }

    @Test
    void updatePersonFailTest() throws Exception {
        Person person = new Person();
        person.setId(53);
        person.setFirstname("Par");
        person.setLastname("Holmstrom");
        person.setEmail("pear@gmail.com");
        person.setPhonenr("07-5851213");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(person);
        //Put
        mockMvc.perform(put("/address")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    void deletePerson() throws Exception {
        String expected = "[{\"id\":2,\"firstname\":\"James\",\"lastname\":\"KI\",\"email\":\"James@gmail.com\",\"phonenr\":\"24-2353485\"},{\"id\":3,\"firstname\":\"Sam\",\"lastname\":\"Django\",\"email\":\"Sam@gmail.com\",\"phonenr\":\"34-85623524\"}]";
        //Delete
        mockMvc.perform(delete("/address/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //Get
        String response = mockMvc.perform(get("/address")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, response);

    }

    @Test
    void deletePersonFailTest() throws Exception {
        //Delete
        mockMvc.perform(delete("/address/155")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}

package mmp.librarymanager;

import ch.qos.logback.core.encoder.EchoEncoder;
import mmp.librarymanager.repositories.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import javax.print.attribute.standard.Media;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@WebMvcTest(ApiController.class)

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ApiController controller;

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookInstanceRepository bookInstanceRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private OperationRepository operationRepository;
    @MockBean
    private PublisherRepository publisherRepository;
    @MockBean
    private ReaderRepository readerRepository;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getAuthors() throws Exception {
        mvc.perform(get("/api/get/authors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void postCorrectReader() throws Exception {
        String resp = """
                {
                    "name": "klsjdf",
                    "address": "",
                    "phone": "+7123456781011",
                    "email": "test@test.test"
                }""";

        mvc.perform(post("/api/post/reader")
                .contentType(MediaType.APPLICATION_JSON).content(resp))
                .andExpect(status().isOk());
    }

    @Test
    public void postIncorrectReaderPhone() throws Exception {
        String resp = """
                {
                    "id": 1,
                    "name": "klsjdf",
                    "address": "",
                    "phone": "lskdjflsjdf",
                    "email": ""
                }""";

        mvc.perform(post("/api/post/reader")
                        .contentType(MediaType.APPLICATION_JSON).content(resp))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("phone", is(true)))
                .andExpect(jsonPath("name", is(false)))
                .andExpect(jsonPath("address", is(false)))
                .andExpect(jsonPath("email", is(false)));
    }

    @Test
    public void postIncorrectReaderEmail() throws Exception {
        String resp = """
                {
                    "id": 1,
                    "name": "klsjdf",
                    "address": "",
                    "phone": "",
                    "email": "salfj@lskdjf.r@"
                }""";

        mvc.perform(post("/api/post/reader")
                        .contentType(MediaType.APPLICATION_JSON).content(resp))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("phone", is(false)))
                .andExpect(jsonPath("name", is(false)))
                .andExpect(jsonPath("address", is(false)))
                .andExpect(jsonPath("email", is(true)));
    }


    @Test
    public void postIncorrectReaderName() throws Exception {
        String resp = """
                {
                    "id": 1,
                    "name": "",
                    "address": "",
                    "phone": "+823352452345",
                    "email": "salfj@lskdjf.r"
                }""";

        mvc.perform(post("/api/post/reader")
                        .contentType(MediaType.APPLICATION_JSON).content(resp))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("phone", is(false)))
                .andExpect(jsonPath("name", is(true)))
                .andExpect(jsonPath("address", is(false)))
                .andExpect(jsonPath("email", is(false)));
    }

    @Test
    public void getPublishers() throws Exception {
        mvc.perform(get("/api/get/publishers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getTitles() throws Exception {
        mvc.perform(get("/api/get/authors")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void bookInfo() throws Exception {
        mvc.perform(get("/api/get/book_info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void bookInstancesBadRequest() throws Exception {
        mvc.perform(get("/api/get/book_instances")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void bookInstances() throws Exception {
        mvc.perform(get("/api/get/book_instances/by_id?id=51")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void operations() throws Exception {
        mvc.perform(get("/api/get/operations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void notFound() throws Exception {
        mvc.perform(get("/not/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

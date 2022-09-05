package mmp.librarymanager;

import mmp.librarymanager.repositories.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
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
}

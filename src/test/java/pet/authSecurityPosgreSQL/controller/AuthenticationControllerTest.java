package pet.authSecurityPosgreSQL.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pet.authSecurityPosgreSQL.dto.UserDTO;
import pet.authSecurityPosgreSQL.dto.UserLoginDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = ("/application-test.properties"))
class AuthenticationControllerTest {

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void exists(){
        assertThat(authenticationController).isNotNull();
    }

    @Test
    @Sql(value = {"/sql/create-user-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql/delete-user-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void loginSuccess() throws Exception {

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("admin");
        userLoginDTO.setPassword("admin");

        ObjectMapper mapper = new ObjectMapper();
        //mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userLoginDTO);

        this.mockMvc.perform(post("/api/login")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    @Sql(value = {"/sql/create-user-before-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql/delete-user-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void loginFailure() throws Exception {

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("admi1");
        userLoginDTO.setPassword("admin");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userLoginDTO);

        this.mockMvc.perform(post("/api/login")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Invalid username or password")));
    }

    @Test
    void addNewUserFailure() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("use");
        userDTO.setEmail("1234");
        userDTO.setFirstName("F");
        userDTO.setLastName("L");
        userDTO.setPassword("pa");
        userDTO.setMatchingPassword("la");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userDTO);

        this.mockMvc.perform(post("/api/sign")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(containsString("fieldErrors")))
                .andExpect(MockMvcResultMatchers.content().string(containsString("globalErrors")));
    }

    @Test
    void addNewUserSuccess() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("username1");
        userDTO.setEmail("username1@gmail.com");
        userDTO.setFirstName("FirstName1");
        userDTO.setLastName("LastName1");
        userDTO.setPassword("password");
        userDTO.setMatchingPassword("password");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(userDTO);

        this.mockMvc.perform(post("/api/sign")
                .contentType("application/json; charset=utf8")
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}
package ru.redmadrobot.red_mad_robot_test;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.redmadrobot.red_mad_robot_test.repository.UserRepository;

import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = BaseTest.BasicInitializer.class)
public abstract class BaseTest {
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("testDB")
                    .withUsername("user")
                    .withPassword("password");

    static {
        POSTGRES.start();
    }

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    public UserRepository userRepository;

    @Value("${application.auth.cookie-name}")
    protected String cookieName;

    public Cookie validityCookie;

    static final class BasicInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRES.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRES.getUsername(),
                    "spring.datasource.password=" + POSTGRES.getPassword()
            ).applyTo(applicationContext.getEnvironment());
        }
    }

    @BeforeEach
    public void getValidityCookie() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "caffsd321");
        jsonObject.put("password", "test1");
        jsonObject.put("role", "NONE_1");
        this.validityCookie = this.mockMvc.perform(post("/api/registration")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().exists("SESSION"))
                .andReturn().getResponse().getCookie(cookieName);
    }

    @AfterEach
    public void deleteEntities() {
        userRepository.deleteAll();
    }
}

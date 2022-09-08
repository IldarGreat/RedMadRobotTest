package ru.redmadrobot.red_mad_robot_test.controllers;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.redmadrobot.red_mad_robot_test.BaseTest;
import ru.redmadrobot.red_mad_robot_test.domain.Role;
import ru.redmadrobot.red_mad_robot_test.domain.User;
import ru.redmadrobot.red_mad_robot_test.dto.LoginRecord;
import ru.redmadrobot.red_mad_robot_test.service.UserService;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class LoginControllerTests extends BaseTest {
    @Autowired
    private UserService userService;

    @Test
    public void registration_thenReturn200() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "test1");
        jsonObject.put("password", "test1");
        jsonObject.put("role", "NONE_1");
        this.mockMvc.perform(post("/api/registration")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.cookie().exists("SESSION"));
    }

    @Test
    public void registrationWithIncorrectData_thenReturn400() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email2", "test1");
        jsonObject.put("password", "test1");
        jsonObject.put("role", "NONE_1");
        this.mockMvc.perform(post("/api/registration")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void registrationWithExistingEmail_thenReturn400() throws Exception {
        userRepository.save(User.builder()
                .email("Testing this case")
                .password("password")
                .role(Role.NONE_1)
                .build());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "Testing this case");
        jsonObject.put("password", "test12");
        jsonObject.put("role", "NONE_1");
        this.mockMvc.perform(post("/api/registration")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void login_thenReturn200() throws Exception {
        userService.save(new LoginRecord(Role.NONE_1, "test", "password"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "test");
        jsonObject.put("password", "password");
        jsonObject.put("role", "NONE_1");
        this.mockMvc.perform(post("/api/login")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.cookie().exists("SESSION"));
    }

    @Test
    public void loginWithIncorrectData_thenReturn400() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email2", "test");
        jsonObject.put("password3", "password");
        jsonObject.put("role", "NONE_1");
        this.mockMvc.perform(post("/api/login")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void loginWithNotExistingPrincipals_thenReturn403() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "not existing");
        jsonObject.put("password", "password");
        jsonObject.put("role", "NONE_1");
        this.mockMvc.perform(post("/api/login")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }
}

package ru.redmadrobot.red_mad_robot_test.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.redmadrobot.red_mad_robot_test.BaseTest;
import ru.redmadrobot.red_mad_robot_test.domain.Advertisement;
import ru.redmadrobot.red_mad_robot_test.repository.AdvertisementRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BulletinBoardControllerTests extends BaseTest {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @BeforeEach
    public void init() {
        advertisementRepository.save(Advertisement.builder()
                .title("TEST1")
                .description("TEST1")
                .photo("TEST1")
                .build());

        advertisementRepository.save(Advertisement.builder()
                .title("TEST2")
                .description("TEST2")
                .photo("TEST2")
                .build());

        advertisementRepository.save(Advertisement.builder()
                .title("TEST3")
                .description("TEST3")
                .photo("TEST3")
                .build());

        advertisementRepository.save(Advertisement.builder()
                .title("TEST4")
                .description("TEST4")
                .photo("TEST4")
                .build());

        advertisementRepository.save(Advertisement.builder()
                .title("TEST5")
                .description("TEST5")
                .photo("TEST5")
                .build());

        advertisementRepository.save(Advertisement.builder()
                .title("TEST6")
                .description("TEST6")
                .photo("TEST6")
                .build());

        advertisementRepository.save(Advertisement.builder()
                .title("TEST7")
                .description("TEST7")
                .photo("TEST7")
                .build());

        advertisementRepository.save(Advertisement.builder()
                .title("TEST8")
                .description("TEST8")
                .photo("TEST8")
                .build());
    }

    @AfterEach
    public void destroy() {
        advertisementRepository.deleteAll();
    }

    @Test
    public void getAllAdverts_thenReturn200() throws Exception {
        this.mockMvc.perform(get("/api/board/")
                        .cookie(validityCookie))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAdvertsWithoutCookie_thenReturn403() throws Exception {
        this.mockMvc.perform(get("/api/board/"))
                .andExpect(MockMvcResultMatchers.status().is(403));
        this.mockMvc.perform(get("/api/board/id")
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().is(403));
        this.mockMvc.perform(get("/api/board/filter"))
                .andExpect(MockMvcResultMatchers.status().is(403));
        this.mockMvc.perform(post("/api/board/add")
                        .param("title", "TEST1")
                        .param("description", "TEST1"))
                .andExpect(MockMvcResultMatchers.status().is(403));
    }

    @Test
    public void getAdvertsById_thenReturn200() throws Exception {
        Advertisement advertisement = advertisementRepository.save(Advertisement.builder()
                .title("TEST9")
                .description("TEST9")
                .photo("TEST9")
                .build());
        this.mockMvc.perform(get("/api/board/id")
                        .cookie(validityCookie)
                        .param("id", String.valueOf(advertisement.getId())))
                .andExpect(jsonPath("$.description").value("TEST9"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Test
    public void getAdvertsByFilter_thenReturn200() throws Exception {
        advertisementRepository.save(Advertisement.builder()
                .title("TEST8")
                .description("TEST8")
                .photo("TEST8")
                .build());
        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("title", "TEST8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("title", "TEST8")
                        .param("description", "TEST8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("title", "TEST8")
                        .param("image", "true"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        advertisementRepository.save(Advertisement.builder()
                .title("TEST1")
                .description("TEST1")
                .photo("TEST1")
                .build());
        advertisementRepository.save(Advertisement.builder()
                .title("TEST1")
                .description("TEST1")
                .photo("TEST1")
                .build());
        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("title", "TEST1")
                        .param("description", "TEST1")
                        .param("image", "true"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("description", "TEST2"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("description", "TEST8")
                        .param("image", "true"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        advertisementRepository.save(Advertisement.builder()
                .title("TEST14")
                .description("TEST8")
                .build());

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("image", "true"))
                .andExpect(jsonPath("$", hasSize(advertisementRepository.findAll().size() - 1)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("image", "true"))
                .andExpect(jsonPath("$", hasSize(advertisementRepository.findAll().size() - 1)))
                .andExpect(MockMvcResultMatchers.status().is(200));

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie)
                        .param("image", "dsadsa"))
                .andExpect(MockMvcResultMatchers.status().is(400));

        this.mockMvc.perform(get("/api/board/filter")
                        .cookie(validityCookie))
                .andExpect(jsonPath("$.*").exists())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void addNewAdvert_thenReturn200() throws Exception {
        this.mockMvc.perform(post("/api/board/add")
                        .cookie(validityCookie)
                        .param("title", "firstadver")
                        .param("description", "firstadver"))
                .andExpect(jsonPath("$.user").exists())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

}

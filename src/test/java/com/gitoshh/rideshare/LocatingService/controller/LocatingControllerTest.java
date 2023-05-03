package com.gitoshh.rideshare.LocatingService.controller;

import com.gitoshh.rideshare.LocatingService.entity.LocationTracker;
import com.gitoshh.rideshare.LocatingService.repo.LocatingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LocatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocatingRepository locatingRepository;

    @BeforeEach
    void setUp() {
        locatingRepository.save(LocationTracker.builder()
                .userId(111L)
                .latitude(1.0)
                .longitude(1.0)
                .build());
    }

    @AfterEach
    void tearDown() {
        locatingRepository.deleteAll();
    }


    @Test
    void getLocationTrackerByUserId() throws Exception {
        // save location tracker
        mockMvc.perform(get("/api/v1/locations/by/user/" + 111L)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createLocationTracker() throws Exception {
        mockMvc.perform(post("/api/v1/locations")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                 {
                                    "latitude": 1.0,
                                    "longitude": 1.0,
                                    "userId": 112,
                                    "isActive": true,
                                    "isDriver": true
                                 }
                                """)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    void getClosestDriver() throws Exception {
        // create location tracker
        locatingRepository.save(LocationTracker.builder()
                .userId(115L)
                .latitude(-1.1907530984957566)
                .longitude(36.771114337065036)
                .isDriver(true)
                .isActive(true)
                .build());

        locatingRepository.save(LocationTracker.builder()
                .userId(114L)
                .latitude(-1.1907530984957566)
                .longitude(36.771114337065036)
                .isDriver(false)
                .isActive(true)
                .build());

        String response = mockMvc.perform(post("/api/v1/locations/closest-driver")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                 {
                                    "latitude": -1.1907530984957766,
                                    "longitude": 36.771114337065036
                                 }
                                """)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).contains("\"userId\":115");
    }

    @Test
    void activateLocationTracker() throws Exception {
        String response = mockMvc.perform(post("/api/v1/locations/by/user/" + 111L + "/activate")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("\"active\":true,");
    }

    @Test
    void deactivateLocationTracker() throws Exception {
        String response = mockMvc.perform(post("/api/v1/locations/by/user/" + 111L + "/deactivate")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("\"active\":false,");
    }
}
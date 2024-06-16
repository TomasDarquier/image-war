package com.tdarquier.imagewar.msvc.users.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.Instant;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindUserByUsername() throws Exception{
        /*
         * Inserted values in H2 DB
         * ('google_1', 'john.doe@example.com', 'johndoe', 'http://example.com/johndoe.jpg', 31)
         */
        String username = "johndoe";

        // get actual time
        Instant now = Instant.now();
        System.out.println("--------------------------------------------------------" + now);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byUsername/{username}", username))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.googleId").value("google_1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.profilePictureUrl").value("http://example.com/johndoe.jpg"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.votesDone").value(311))
        // check if the timestamp has an acceptable value (<30s of difference)
        .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", 
            allOf(
                greaterThanOrEqualTo(now.minusSeconds(30).toString()),
                lessThanOrEqualTo(now.toString())
            )
        ));
    }

    @Test
    void shouldReturnNotFoundForInvalidUsername() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/byUsername/{username}", "FakeUser"))
        .andExpect(status().isNotFound());
    }
}

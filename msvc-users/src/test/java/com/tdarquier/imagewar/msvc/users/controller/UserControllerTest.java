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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdarquier.imagewar.msvc.users.entity.User;
import com.tdarquier.imagewar.msvc.users.service.UserService;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired 
    UserService userService;

    @Test
    void shouldFindUserByUsername() throws Exception{
        /*
         * value in H2 DB
         * ('google_1', 'john.doe@example.com', 'johndoe', 'http://example.com/johndoe.jpg', 31)
         */
        String username = "johndoe";

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byUsername/{username}", username))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.googleId").value("google_1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.profilePictureUrl").value("http://example.com/johndoe.jpg"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.votesDone").value(311))
        .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").exists()); 
    }

    @Test
    void shouldReturnNotFoundForInvalidUsername() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/byUsername/{username}", "FakeUser"))
        .andExpect(status().isNotFound());
    }


    @Test
    void shouldDeleteUser() throws Exception{
        /*
         * value in H2 DB
         * ('google_2', 'jane.smith@example.com', 'janesmith', 'http://example.com/janesmith.jpg', 0)
         */

        User user = userService.getUserByUsername("janesmith").get(); 

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byUsername/{username}", user.getUsername()))
            .andExpect(status().isNotFound());
    }

    @Test
    void shouldAddVote() throws Exception{
        /*
         * ('google_3', 'mike.jones@example.com', 'mikejones', 'http://example.com/mikejones.jpg', 7)
         */

         User user = userService.getUserByUsername("mikejones").get();

         mockMvc.perform(MockMvcRequestBuilders.put("/users/vote")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byUsername/{username}", user.getUsername())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.votesDone").value(8));
    }

    @Test
    void shouldChangeUsername() throws Exception{

        User user = userService.getUserByUsername("emilydavis").get();
        String newUsername = "emilypeterson";

        mockMvc.perform(MockMvcRequestBuilders.put("/users/change_username/{newUsername}", newUsername)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/byUsername/{username}", newUsername)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("emilypeterson"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.changedUsername").value(true));
            

    }
}

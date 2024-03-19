package com.example.sprinwebmvctest.controler;

import com.example.sprinwebmvctest.model.Task;
import com.example.sprinwebmvctest.repository.RepositoryTask;
import com.example.sprinwebmvctest.service.ServiceTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServiceTask serviceTask;
    @Autowired
    private RepositoryTask repositoryTask;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("INTEGRATION")
    void shouldSaveTaskINT() throws Exception {
        Task task1 = new Task("task1", 5);


        mockMvc.perform(post("/api/save").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно добавлен"));

    }

    @Test
    @DisplayName("INTEGRATION")
    void shouldUpdateTaskINT() throws Exception {
        int id=15;
        Task task2 = new Task("Vasya", 5);


        mockMvc.perform(put("/api/update/"+id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task2)))
                .andExpect(status().isOk())
                .andExpect(content().string("task updated"));

    }


    @Test
    @DisplayName("INTEGRATION")
    void shouldTeturnTaskById() throws Exception {
        int id=15;


        mockMvc.perform(get("/api/findById/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("task2"))
                .andExpect(jsonPath("$.grade").value(8));

    }


    @Test
    @DisplayName("INTEGRATION")
    void shoulddeleteTaskById() throws Exception {
        int id=15;


        mockMvc.perform(delete("/api/delete/"+id))
                .andExpect(status().isAccepted());

    }


    @Test
    @DisplayName("INTEGRATION")
    void shouldReturnTheGreatestTask() throws Exception {

        mockMvc.perform(get("/api/getGreat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value(12));

    }

    @Test
    @DisplayName("INTEGRATION")
    void shouldReturnTheAvgGradeFromTaskINT() throws Exception {
        Task task1 = new Task("task1", 5);
        Task task2 = new Task("task2", 8);
        Task task3 = new Task("task3", 10);


        serviceTask.save(task1);
        serviceTask.save(task2);
        serviceTask.save(task3);

        double avg = 7.666666666666667;


        mockMvc.perform(get("/api/avg"))
                .andExpect(status().isOk())
                .andExpect(content().string("7.666666666666667"));

    }



    @Test
    @DisplayName("INTEGRATION")
    void shouldReturnTheSumGradeFromTaskINT() throws Exception {
        Task task1 = new Task("task1", 5);
        Task task2 = new Task("task2", 8);
        Task task3 = new Task("task3", 10);


        serviceTask.save(task1);
        serviceTask.save(task2);
        serviceTask.save(task3);

        double avg = 23;

        mockMvc.perform(get("/api/sum"))
                .andExpect(status().isOk())
                .andExpect(content().string("23"));

    }

}

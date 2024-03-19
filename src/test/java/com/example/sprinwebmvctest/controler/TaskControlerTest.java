package com.example.sprinwebmvctest.controler;

import com.example.sprinwebmvctest.model.Task;
import com.example.sprinwebmvctest.repository.RepositoryTask;
import com.example.sprinwebmvctest.service.ServiceTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskControler.class)
class TaskControlerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceTask serviceTask;
    @MockBean
    private RepositoryTask repositoryTask;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ShoudCreateTask() throws Exception {
        Task task = new Task("22", 2);

        given(serviceTask.save(task)).willReturn(task);
        mockMvc.perform(post("/api/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(content().string("Успешно добавлен"));
    }

    @Test
    void ShoudfindAllTask() throws Exception {
        List<Task> list = new ArrayList<>();
        list.add(new Task("admin", 5));
        list.add(new Task("44", 4));
        list.add(new Task("tas", 4));
        given(serviceTask.findAll()).willReturn(list);
        //given(repositoryTask.findAll()).willReturn(list);
        mockMvc.perform(get("/api/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)))
                .andExpect(jsonPath("$[2].grade").value(4));
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        Task task = new Task("admin", 5);
        int id = 3;
        when(serviceTask.findById(id)).thenReturn(task);

        mockMvc.perform(get("/api/findById/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value(5))
                .andExpect(content().json(objectMapper.writeValueAsString(task)));

    }


    @Test
    void shouldReturnUpdatedTask() throws Exception {
        Task task = new Task("admin", 5);
        Task updated = new Task("adminNew", 5);
        int id = 4;
        given(serviceTask.update(id, task)).willReturn(updated);

        mockMvc.perform(put("/api/update/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.grade").value(5)) В ОТВЕТЕ У НАС ВОЗВАРАЩАЕТСЯ СТРИНГ А НЕ TASK!!
                .andExpect(content().string("task updated"));

    }


    @Test
    void shouldReturnDeletedStatusTask() throws Exception {
        Task task = new Task("admin", 5);

        int id = 4;
        Mockito.doNothing().when(serviceTask).delete(id);

        mockMvc.perform(delete("/api/delete/" + id))
                .andExpect(status().isAccepted());

        verify(serviceTask).delete(id);


    }


    @Test
    void shouldReturnTheGreatestGradeFromTask() throws Exception {
        Task task = new Task("admin", 5);

        given(serviceTask.theGreatest()).willReturn(task);

        mockMvc.perform(get("/api/getGreat"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(task)));

    }

    @Test
    void shouldReturnTheAvgGradeFromTask() throws Exception {
        double avg = 6.2;

        given(serviceTask.avg()).willReturn(avg);

        mockMvc.perform(get("/api/avg"))
                .andExpect(status().isOk())
                .andExpect(content().string("6.2"))
                .andDo(print());

    }
    @Test
    void shouldReturnTheSumGradeFromTaskMock() throws Exception {
        given(serviceTask.sum()).willReturn(34.);


        mockMvc.perform(get("/api/sum"))
                .andExpect(status().isOk())
                .andExpect(content().string("34"));

    }



}
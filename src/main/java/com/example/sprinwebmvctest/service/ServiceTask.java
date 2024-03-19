package com.example.sprinwebmvctest.service;

import com.example.sprinwebmvctest.model.Task;
import com.example.sprinwebmvctest.repository.RepositoryTask;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceTask {
    private final RepositoryTask repositoryTask;

    public List<Task> findAll() {
        List<Task> all = repositoryTask.findAll();
        return all;

    }

    public Task findById(int id) {
        Optional<Task> byId = repositoryTask.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new RuntimeException();
        }
    }

    public Task update(int id, Task task) {
        Optional<Task> byId = repositoryTask.findById(id);
        if (byId.isPresent()) {
            return repositoryTask.save(task);
        } else {
             throw new RuntimeException();
        }
    }

    public void delete(int id) {
        Optional<Task> task = repositoryTask.findById(id);

        if(!task.isPresent()){
            throw new RuntimeException();
        }

         repositoryTask.delete(task.get());
    }

    public Task save(Task task) {
      return   repositoryTask.save(task);

    }

    public Task theGreatest() {
        return repositoryTask.great();
    }

    public double avg() {
        return repositoryTask.avgSum();
    }

    public double sum() {
        return repositoryTask.sum();
    }
}

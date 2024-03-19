package com.example.sprinwebmvctest.controler;

import com.example.sprinwebmvctest.dto.DtoTask;
import com.example.sprinwebmvctest.model.Task;
import com.example.sprinwebmvctest.repository.RepositoryTask;
import com.example.sprinwebmvctest.service.ServiceTask;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskControler {
    private final ServiceTask serviceTask;

    @GetMapping("/findAll")
    public ResponseEntity<List<Task>> findAll() {
        List<Task> all = serviceTask.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping("/findById/{id}")
    public ResponseEntity<Task> findById(@PathVariable int id) {
        Task task = serviceTask.findById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Task task) {
        Task update = serviceTask.update(id, task);
        return ResponseEntity.ok().body("task updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable int id) {
         serviceTask.delete(id);
         return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Task task) {
        Task task1 = serviceTask.save(task);
        return ResponseEntity.ok("Успешно добавлен");
    }

    @GetMapping("/getGreat")
    public ResponseEntity<Task> getTheGreatest(){
        Task task = serviceTask.theGreatest();
        return ResponseEntity.ok(task);
    }


    @GetMapping("/avg")
    public ResponseEntity<Double> doubleResponseEntity(){
        double d = serviceTask.avg();
        return ResponseEntity.ok(d);
    }

    @GetMapping("/sum")
    public ResponseEntity<Double> sumResponseEntity(){
        double d = serviceTask.sum();
        return ResponseEntity.ok(d);
    }

}

package com.example.sprinwebmvctest.repository;

import com.example.sprinwebmvctest.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTask extends JpaRepository<Task,Integer> {

    @Query(value = "select * from task order by grade desc limit 1",nativeQuery = true)
    Task great();

    @Query(value = "select avg(grade) from task",nativeQuery = true)
    double avgSum();

    @Query(value = "select sum(grade) from task",nativeQuery = true)
    double sum();
}

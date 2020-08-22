package com.cisco.restAPI.repository;

import com.cisco.restAPI.dataObjects.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer> {
}

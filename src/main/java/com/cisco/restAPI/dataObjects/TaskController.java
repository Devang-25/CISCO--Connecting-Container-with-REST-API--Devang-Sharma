package com.cisco.restAPI.dataObjects;

import com.cisco.restAPI.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @CrossOrigin
    @GetMapping
    public Object getAllTheTasks(){
        if(taskRepository.count() == 0)
            return new ResponseEntity<Error>(new Error("failed","There is No Content in Database"), HttpStatus.NO_CONTENT);
        return taskRepository.findAll();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> addNewTask(@RequestBody(required = false) Task task){
        if(task == null || task.getData().equals(""))
            return new ResponseEntity<Error>(new Error("failed","Task Cannot Be Empty"), HttpStatus.BAD_REQUEST);
        this.taskRepository.save(task);
        return new ResponseEntity<Success>(new Success("Success","Added"), HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<?> updateOldTask(@RequestBody(required = false) ArrayList<Task> newTasks){

        if(newTasks == null || newTasks.size() != 2){
            return new ResponseEntity<>(new Error("failed","Put accepts exactly two parameters"), HttpStatus.BAD_REQUEST);
        }

        Task oldTask = newTasks.get(0);
        if(oldTask == null || oldTask.getId() == 0 || oldTask.getData().equals("") || oldTask.getData() == null){
            return new ResponseEntity<>(new Error("failed","The Task being modify doesn't Exists"), HttpStatus.BAD_REQUEST);
        }
        Task newTask = newTasks.get(1);
        if(newTask == null || newTask.getId() == 0 || newTask.getData().equals("")){
            return new ResponseEntity<>(new Error("failed","The new Task Cannot Be Empty"), HttpStatus.BAD_REQUEST);
        }

        oldTask = taskRepository.findById(oldTask.getId()).orElseThrow(()->{ throw new ResourceAccessException("Unable to find the Task");
        });

        oldTask.setData(newTask.getData());
        this.taskRepository.save(oldTask);
        return new ResponseEntity<>(new Success("Success","Modified"), HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<?> deleteOldTask(@RequestBody(required = false) Task oldTask){
        if(oldTask == null || oldTask.getId() == 0){
            return new ResponseEntity<Error>(new Error("failed","The Task Being Delete Cannot be Empty"), HttpStatus.BAD_REQUEST);
        }

        oldTask = this.taskRepository.findById(oldTask.getId()).orElseThrow(()->new ResourceAccessException("Unable to find the Task"));
        this.taskRepository.delete(oldTask);

        return new ResponseEntity<Success>(new Success("Success",
                "Deleted"), HttpStatus.OK);
    }

}

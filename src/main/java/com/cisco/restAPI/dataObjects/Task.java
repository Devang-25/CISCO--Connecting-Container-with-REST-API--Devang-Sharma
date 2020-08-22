package com.cisco.restAPI.dataObjects;


import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    @Id
    private int id;

    @Column(name= "data")
    private String data;

    public Task() {
    }

    public Task(int id, String data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

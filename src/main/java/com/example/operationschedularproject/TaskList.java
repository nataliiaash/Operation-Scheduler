package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.io.Serializable;

public class TaskList implements Serializable {

    LinkedList<Task> taskLinkedList = new LinkedList<>();


    public void add(Task task){
        taskLinkedList.addLast(task);
    }

    public void delete(Task task) {
        try{
        taskLinkedList.remove(taskLinkedList.find(task));
    } catch (EmptyException e){
            System.out.println("no such task");
        }
    }
    public void edit(Task task, Task newTask){
        delete(task);
        add(newTask);
    }
}

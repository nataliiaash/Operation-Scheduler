package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;

public class TaskList {

    LinkedList<Task> taskList;

    public TaskList() {
        this.taskList = new LinkedList<>();
    }

    public void add(String description, TaskPriority priority){
        Task newTask = new Task(description, priority);
        int i=0;
        while (i<taskList.size()){
            Task tempTask = taskList.get(i).value;
            if (tempTask.getDescription().equals(description)){
                System.out.println("Doctor already has this task.");
                    return;
                }
            i++;
            }
            taskList.addLast(newTask);
        }


    public void delete(String taskDescription){
        int i=0;
        while(i<taskList.size()){
            Task tempTask = taskList.get(i).value;
            if (tempTask.getDescription().equals(taskDescription)){
                try {
                    taskList.remove(i);
                    System.out.println("Task is successfully deleted.");
                }
                catch (EmptyException e){
                    System.out.println("There is no such task.");
                }
                return;
            }
            i++;
        }
        System.out.println("There is no such task.");
    }
    public void edit(Task task, String newDescription, TaskPriority newPriority){
        try {
            taskList.remove(taskList.find(task));
            Task newTask = new Task(newDescription, newPriority);
            taskList.addLast(newTask);

        }
        catch (EmptyException e){
            System.out.println("Doctor does not have this task");
        }
    }
    public void viewAllTasks(){
        taskList.printList();
    }

    public static void main(String[] args) {
        TaskList list = new TaskList();
        list.add("task1", TaskPriority.low);
        list.add("task2", TaskPriority.medium);
        list.add("task3", TaskPriority.medium);
        list.delete("task4");
        list.viewAllTasks();
    }
}

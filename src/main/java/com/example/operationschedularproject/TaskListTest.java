package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    private TaskList taskList;
    private LinkedList<Task> taskLinkedList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        taskLinkedList = new LinkedList<>();
        taskList.taskLinkedList = taskLinkedList;
    }

    @Test
    void add_ValidTask_AddsToLinkedList() {
        Task task = new Task();

        taskList.add(task);

        assertTrue(taskLinkedList.contains(task));
    }

    @Test
    void delete_ExistingTask_RemovesFromLinkedList() {
        Task task = new Task();
        taskLinkedList.addLast(task);

        taskList.delete(task);

        assertFalse(taskLinkedList.contains(task));
    }

    @Test
    void delete_NonExistingTask_DoesNotRemoveFromLinkedList() {
        Task task = new Task();

        taskList.delete(task);

        assertFalse(taskLinkedList.contains(task));
    }

    @Test
    void edit_ExistingTask_ReplacesWithNewTask() {
        Task task = new Task();
        Task newTask = new Task();
        taskLinkedList.addLast(task);

        taskList.edit(task, newTask);

        assertFalse(taskLinkedList.contains(task));
        assertTrue(taskLinkedList.contains(newTask));
    }

    @Test
    void edit_NonExistingTask_DoesNotModifyLinkedList() {
        Task task = new Task();
        Task newTask = new Task();

        taskList.edit(task, newTask);

        assertFalse(taskLinkedList.contains(task));
        assertFalse(taskLinkedList.contains(newTask));
    }

    // Add more test methods for other scenarios in the TaskList class

}

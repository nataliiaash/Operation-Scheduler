package com.example.operationschedularproject.LinkedList;

import java.io.Serializable;

public class Node<T> implements Serializable {
    public T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = next;
    }
}

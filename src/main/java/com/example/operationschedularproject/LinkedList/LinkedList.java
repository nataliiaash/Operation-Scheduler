package com.example.operationschedularproject.LinkedList;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;
    public LinkedList(){
        this.size = 0;
    }

    public Node<T> get(int index){
        Node temp = head;
        for (int i = 0; i < index; i++){
            temp = temp.next;
        }
        return temp;
    }

    public int find(T value) throws EmptyException {
        Node temp = head;
        int index = 0;
        while (temp != null){
            if(temp.value == value){
                return index;
            }
            index++;
            temp = temp.next;
        }
        throw new EmptyException("Not found");
    }

    public void addFirst(T value){
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if(tail == null){
            tail = head;
        }
        this.size++;
    }
    public void addLast(T value){
        if(tail == null){
            addFirst(value);
            return;
        }
        Node<T> node = new Node<>(value);
        tail.next = node;
        tail = node;
        this.size++;
    }
    public void add(T value, int index){
        if(index == 0){
            addFirst(value);
            return;
        }
        if(index == this.size){
            addLast(value);
            return;
        }
        Node temp = head;
        for (int i = 1; i < index; i++){
            temp = temp.next;
        }
        Node<T> node = new Node<T>(value, temp.next);
        temp.next = node;
        this.size++;
    }
    public T removeFirst() throws EmptyException {
        if(isEmpty()){
            throw new EmptyException("No element exits here");
        }
        T value = head.value;
        head = head.next;
        if(head == null){
            tail = null;
        }
        this.size--;
        return value;
    }
    public T removeLast() throws EmptyException {
        if(size <=1){
            return removeFirst();
        }
        Node secondLast = get(size - 2);
        T value = tail.value;
        tail = secondLast;
        tail.next = null;
        this.size--;
        return value;
    }
    public T remove(int index) throws EmptyException {
        if(index == 0){
            return removeFirst();
        }
        if (index == size - 1){
            return removeLast();
        }
        Node node =  get(index - 1);
        T value = (T) node.next.value;
        node.next = node.next.next;
        this.size--;
        return value;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public T getHeadData(){
        return this.head.value;
    }
    public void printList(){
        Node temp = head;
        while (temp != null){
            System.out.println(temp.value);
            temp = temp.next;
        }
    }
}
package com.example.operationschedularproject;

import com.example.operationschedularproject.LinkedList.EmptyException;
import com.example.operationschedularproject.LinkedList.LinkedList;

import java.io.Serializable;

public class MachineBooker implements Serializable {
    private LinkedList<Machine> machineLinkedList = new LinkedList<>();
    public void add(Machine machine){
        machineLinkedList.addLast(machine);
    }
    public void delete(Machine machine){
        try {
            machineLinkedList.remove(machineLinkedList.find(machine));
        }
        catch(EmptyException e){
            System.out.printf("machine not found");
        }
    }
    public void edit(Machine oldMachine, Machine newMachine){
        delete(oldMachine);
        add(newMachine);
    }

    public LinkedList<Machine> getMachineLinkedList() {
        return machineLinkedList;
    }

    public void setMachineLinkedList(LinkedList<Machine> machineLinkedList) {
        this.machineLinkedList = machineLinkedList;
    }
}

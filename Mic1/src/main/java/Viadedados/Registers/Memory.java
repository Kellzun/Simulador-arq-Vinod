/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arthu
 */
public class Memory {
    private List<int[]> memory_storage;
    
    public Memory(int size){
       this.memory_storage = new ArrayList<int[]>(size);
    } 

    public List<int[]> getMemory_storage() {
        return memory_storage;
    }

    public void setMemory_storage(List<int[]> memory_storage) {
        this.memory_storage = memory_storage;
    }
    
    public int[] getpositionMemory(int position) {
        return this.memory_storage.get(position);
    }
    
}

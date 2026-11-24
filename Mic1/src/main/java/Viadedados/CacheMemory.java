/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados;

import Viadedados.MainMemory;

/**
 *
 * @author arthu
 */
public class CacheMemory {
    private static final int SIZE = 256;
    
    private int[][] data = new int[SIZE][16];
    private int[] tags = new int[SIZE];
    private boolean[] valid = new boolean[SIZE];

    private int hits = 0;
    private int misses = 0;

    public int[] read(int address, MainMemory memory) {

        int index = address % SIZE;

        // Cache hit
        if (valid[index] && tags[index] == address) {
            hits++;
            return data[index].clone();
        }

        // Cache miss
        misses++;

        int[] value = memory.read_address(address);

        data[index] = value.clone();
        tags[index] = address;
        valid[index] = true;

        return value;
    }

    public void write(int address, int[] value, MainMemory memory) {

        int index = address % SIZE;

        // Write-through
        memory.write_data(value, address);

        data[index] = value.clone();
        tags[index] = address;
        valid[index] = true;
    }

    public void clear() {
        data = new int[SIZE][16];
        tags = new int[SIZE];
        valid = new boolean[SIZE];

        hits = 0;
        misses = 0;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int getSize() {
        return SIZE;
    }

    public boolean isValidAt(int index) {
        return valid[index];
    }

    public int getAddressAt(int index) {
        return tags[index];
    }

    public int[] getDataAt(int index) {
        return data[index].clone();
    }

    public double getHitRate() {
        int total = hits + misses;

        if (total == 0) {
            return 0;
        }

        return (double) hits / total * 100;
    }
}

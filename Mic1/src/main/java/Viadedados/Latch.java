/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados;

/**
 *
 * @author arthu
 */
public class Latch {
    private static final int WORD = 16;
    private int[] value_stored = new int[WORD];
    
    public Latch() {
        for (int i = 0; i < WORD; i++) {
            this.value_stored[i] = 0;
        }
    }

    public int[] getValue_stored() {
        return value_stored;
    }

    public void setValue_stored(int[] value_stored) {
        this.value_stored = value_stored;
    }
    
    
}

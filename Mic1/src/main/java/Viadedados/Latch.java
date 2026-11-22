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
    private int[] value_stored;

    public Latch(int[] value_stored) {
        this.value_stored = value_stored;
    }

    public int[] getValue_stored() {
        return value_stored;
    }

    public void setValue_stored(int[] value_stored) {
        this.value_stored = value_stored;
    }
    
    
}

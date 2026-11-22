/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class Smask extends Register {
    private final int[] register = new int[]{0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1};

    public Smask(int size_bits) {
        super(size_bits);
    }
    
}

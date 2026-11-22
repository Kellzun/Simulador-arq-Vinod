/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class Amask extends Register {
    private final int[] register = new int[]{0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1};

    public Amask(int size_bits) {
        super(size_bits);
    }
    
    
}
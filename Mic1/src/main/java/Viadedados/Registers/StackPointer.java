/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class StackPointer extends Register {

    public StackPointer(int tamanho_bits) {
        super(tamanho_bits);
        this.register[3] = 1;
    }
    
}

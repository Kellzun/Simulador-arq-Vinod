/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class RegisterMinusOne extends Register {

    public RegisterMinusOne(int size_bits) {
        super(size_bits);
        for(int i=0; i<size_bits; i++) {
            this.register[i] = 1;
        }
    }
    
}

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

    public RegisterMinusOne(int tamanho_bits) {
        super(tamanho_bits);
        for(int i=0; i<tamanho_bits; i++) {
            this.register[i] = 1;
        }
    }
    
}

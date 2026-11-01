/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class Register { // usaremos para todos os outros registradores e sem alterações para os A, B, C, D, E e F.
    protected int[] register; //vetor de int que guarda em binário 1 e 0
    
    public Register(int tamanho_bits) { //vamos definir tamanho_bits = 16
        this.register = new int[tamanho_bits]; //definindo registrador e seu tamanho de bits
    }
    
    public int[] getRegister() { //enviando o que está guardado no registrador
        int[] data = new int[this.register.length];
        for (int i=0; i<this.register.length;i++) {
            data[i] = this.register[i];
        }    
        return data;
    }

    public void setRegister(int[] memory_line) { //receber palavra da memoria
        for (int i=0; i<register.length; i++) {
            this.register[i] = memory_line[i];
        }
    }
}

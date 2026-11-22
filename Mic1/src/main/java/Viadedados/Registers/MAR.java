/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class MAR extends Register {
    public final int NUM_ADDRESS = 4;

    // register terá tamanho de 12 bits
    public MAR(int size_bits) { // o MAR só guarda 12 bits. Logo, pegará os 12 bits que indicam endereço
        super(size_bits);
    }
    
    public void load(int[] latchB) {// MAR receberá endereço do latch B (tem 16 bits)
        int counter = 0;
        for (int i = NUM_ADDRESS; i < latchB.length; i++) {
            this.register[counter] = latchB[i]; counter++;
        }
    }
    
    public int[] getValue() {
        return this.register.clone();
    }
    
    public int toAddress() {
        int address = 0;
        for (int bit : this.register) {
            address = (address << 1) | bit;
        }
        return address;
    }
}

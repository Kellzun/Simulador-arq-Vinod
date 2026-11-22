/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados;

/**
 *
 * @author arthu
 */
public class ALU {
    private static final int WORD = 16;
    private int Nbit, Zbit;
    private int[] output = new int[WORD];
    
    // guia ULA
    // ULA | SAIDA
    // 00 -> A + B
    // 01 -> A AND B
    // 10 -> A
    // 11 -> NOT A
    // op já vem como decimal
    
    public ALU() {
    }
    
    public int[] operateALU(int[] a, int[] b, int op) {
        
        if (op == 0) {
            int carry = 0;
            for (int i = WORD - 1; i >= 0; i--) {
                int sum = a[i] + b[i] + carry;
                this.output[i] = sum % 2;
                carry = sum / 2;
            }
        }
        if (op == 1) {
            for (int i = WORD - 1; i >= 0; i--) {
                this.output[i] = a[i] * b[i];
            }
        }
        if (op == 2) {
            for (int i = WORD - 1; i >= 0; i--) {
                this.output[i] = a[i];
            }
        }
        if (op == 3) {
            for (int i = WORD - 1; i >= 0; i--) {
                if (a[i] == 0) this.output[i] = 1;
                else this.output[i] = 0;
            }
        }
        
        this.Nbit = this.output[0];
        this.Zbit = 1;
        for (int i = 0; i < WORD; i++) {
            if (this.output[i] == 1) {
                this.Zbit = 0;
                break;
            }
        }
        return this.output;
    }
    
    public int getN() { return this.Nbit; }
    public int getZ() { return this.Zbit; }
}

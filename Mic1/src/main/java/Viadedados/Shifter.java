/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados;

/**
 *
 * @author arthu
 */
public class Shifter {
    private int[] data; // aqui, recebemos 16 bits
    private int op; // na operação, temos 2 bits
    private final int FIRST_BIT = 0;
    private final int FINAL_BIT = 15;
    private final int SHIFT_RIGHT = 1;
    private final int SHIFT_LEFT = 2;
    private final int MAINTAIN = 0;
    // guia da op
    // 00 -> não faz nada
    // 01 -> desloca 1 bit para direita
    // 10 -> desloca 1 bit para esquerda
    // 11 -> não utilizamos

    public Shifter() {
    }
    
    public int[] apply_operation(int[] data, int op) {
        this.data = data; this.op = op;
        
        
        if (op == MAINTAIN) return data;
        
        
        if (op == SHIFT_RIGHT) { // desloca 1 bit para direita
            for (int i = this.data.length - 1; i > 0; i--) {
                this.data[i] = this.data[i-1];
            }
            this.data[FIRST_BIT] = 0;
        }
        if (op == SHIFT_LEFT) { // desloca 1 bit para esquerda
            for (int i = 0; i < this.data.length - 1; i++) {
                this.data[i] = this.data[i+1];
            }
            this.data[FINAL_BIT] = 0;
        }
        return data;
    }
    

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Unidade_de_Controle;

/**
 *
 * @author arthu
 */
public class FlowControl {
    private final int NO_JUMP = 0;
    private final int JUMP_IF_N = 1;
    private final int JUMP_IF_Z = 2;
    private final int JUMP_ALWAYS = 3;
    
    
    private int Nbit; // recebe o bit N e o bit Z da ULA
    private int Zbit;
    private int Mbit; // retorna o bit M
    private int[] cond; // vindo do MIR
    
    
    // guia do cond
    // M = 0 -> MMUX deixa passar MPC + 1
    // M = 1 -> MMUX deixa passar o ADDR(salto)
    // 00 -> M = 0 -> não salta
    // 01 -> M = N -> salta se N = 1
    // 10 -> M = Z -> salta se Z = 1
    // 11 -> M = 1 -> salta sempre
    

    public int getNbit() {
        return Nbit;
    }

    public void setNbit(int Nbit) {
        this.Nbit = Nbit;
    }

    public int getZbit() {
        return Zbit;
    }

    public void setZbit(int Zbit) {
        this.Zbit = Zbit;
    }

    public int[] getCond() {
        return cond;
    }

    public void setCond(int[] cond) {
        this.cond = cond;
    }
    
    public int getM() {
        return this.Mbit;
    }
    
    public int calculateM(int[] cond, int N, int Z) {
        this.Nbit = N; this.Zbit = Z; this.cond = cond;
        int operation_decimal= 0;
        for (int i = 0; i < this.cond.length; i++) {
            // conversão de binário para decimal
            operation_decimal = (operation_decimal << 1) | this.cond[i];
        }
        if (operation_decimal == NO_JUMP) this.Mbit = 0; 
        if (operation_decimal == JUMP_IF_N) this.Mbit = this.Nbit; 
        if (operation_decimal == JUMP_IF_Z) this.Mbit = this.Zbit;
        if (operation_decimal == JUMP_ALWAYS) this.Mbit = 1;
        return this.Mbit;
    }
}

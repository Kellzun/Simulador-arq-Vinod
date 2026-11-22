/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Unidade_de_Controle;

import Viadedados.Registers.Register;

/**
 *
 * @author arthu
 */
public class MPC extends Register {

    public MPC(int size_bits) { // size_bits = 8
        super(size_bits);
        this.register = new int[] {0,0,0,0,0,0,0,0};
    }
    
    public void reset_MPC() {
        this.register = new int[] {0,0,0,0,0,0,0,0};
    }
    
    public int[] incrementMPC() { //para atualizar de fato o MPC, basta dar um set com o next
        int[] next = this.register.clone();
        for (int i = next.length - 1; i >= 0; i--) {
            if (next[i] == 0) {
                next[i] = 1; 
                break;
            }
            else {
                next[i] = 0;
            }
        }
        return next;
    }
    
}

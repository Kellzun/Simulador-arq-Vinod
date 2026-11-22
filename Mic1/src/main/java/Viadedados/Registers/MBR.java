/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class MBR extends Register { // MBR guardará o dado

    public MBR(int size_bits) { // size_bits = 16
        super(size_bits);
    }
    
    public void load(int[] source) {
        for (int i=0; i<this.register.length;i++) {
            this.register[i] = source[i];
        }
    }
    
    public int[] write() {
        return this.register.clone();
   }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Montador;

/**
 *
 * @author arthu
 */
public class Conversion {
    
    // vetor de bits (índice 0 = MSB) -> inteiro sem sinal
    public static int binaryToDecimal(int[] bits) {
        int v = 0;
        for (int b : bits) {
            v = (v << 1) | b;
        }
        return v;
    }

    // inteiro -> vetor de bits (índice 0 = MSB)
    public static int[] decimalToBinary(int value, int size) {
        int[] bits = new int[size];
        for (int i = 0; i < size; i++) {
            bits[i] = (value >> (size - 1 - i)) & 1;
        }
        return bits;
    }

    // formata um vetor de bits como hexadecimal, só para exibição
    public static String toHex(int[] bits) {
        return String.format("0x%0" + (bits.length / 4) + "X", binaryToDecimal(bits));
    }
    
}

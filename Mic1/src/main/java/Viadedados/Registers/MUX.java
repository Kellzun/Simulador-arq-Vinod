/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arthu
 */
public class MUX {
    private final List<int[]> inputs;
    private final int wordSize;
    private int[] control;
    // tanto o AMux e Smux serão instâncias dessa classe
    
    public MUX(int numInputs, int wordSize, int controlSize) {
        this.wordSize = wordSize;
        this.inputs = new ArrayList<>();
        for (int i = 0; i < numInputs; i++) {
            inputs.add(new int[wordSize]);   // preenche a lista de verdade
        }
        this.control = new int[controlSize];
    }
    public void setInput(int index, int[] bits) { // manda a pos da entrada e o dado
        if (index < 0 || index >= inputs.size()) {
            throw new IllegalArgumentException(
                "Índice de entrada inválido: " + index);
        }
        validateBits(bits, wordSize, "entrada");
        inputs.set(index, bits.clone());
    }

    public void setControl(int[] bits) {
        validateBits(bits, control.length, "controle");
        this.control = bits.clone();
    }

    public int[] getOutput() {
        int index = controlToIndex();
        if (index >= inputs.size()) {
            // No MIC-1, o campo B tem 4 bits (16 valores) mas só 9 registradores.
            // Aqui devolvemos zeros, como um barramento sem nenhum registrador ligado.
            return new int[wordSize];
        }
        return inputs.get(index).clone();
    }

    // Converte os bits de controle em inteiro (bit 0 = mais significativo)
    private int controlToIndex() {
        int index = 0;
        for (int bit : control) {
            index = (index << 1) | bit;
        }
        return index;
    }

    private void validateBits(int[] bits, int expectedSize, String nome) {
        if (bits == null || bits.length != expectedSize) {
            throw new IllegalArgumentException(
                "Vetor de " + nome + " deve ter " + expectedSize + " bits");
        }
        for (int b : bits) {
            if (b != 0 && b != 1) {
                throw new IllegalArgumentException(
                    "Bits devem ser 0 ou 1, recebido: " + b);
            }
        }
    }
}

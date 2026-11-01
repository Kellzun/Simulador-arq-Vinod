/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados.Registers;

/**
 *
 * @author arthu
 */
public class TemporaryInstructionRegister extends InstructionRegister {
// o TIR serve para copiar e guardar IR e ir deslocando para esquerda até conseguir o opcode,
// destruindo o conteúdo dele no processo
    public TemporaryInstructionRegister(int tamanho_bits) {
        super(tamanho_bits);
    }
    
}

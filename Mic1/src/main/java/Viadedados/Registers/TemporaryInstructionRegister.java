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
// as classes podem estar parecendo semelhantes umas as outras. Entretanto, é importante criarmos elas
// para que o funcionamento do simulador fique mais didático
    
    public TemporaryInstructionRegister(int size_bits) {
        super(size_bits);
    }
    
}

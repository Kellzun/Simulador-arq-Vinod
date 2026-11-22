/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Unidade_de_Controle;

import Viadedados.Registers.Register;
import Viadedados.Registers.*;
import java.util.ArrayList;

/**
 *
 * @author arthu
 */
public class RegistersCollection{
    private ArrayList<Register> Registers;
    private final int SIZE_BITS_DATA_PATH = 16;
    private final int USABLE_REGISTERS = 6;
    private final int USABLE_REGISTERS_BEGIN_AT = 10;
    
    // essa classe guardará TODOS os registradores da via de dados com exceção do MBR e do MAr
    public RegistersCollection() {
       this.Registers.add(new Accumulator(SIZE_BITS_DATA_PATH));
       this.Registers.add(new Amask(SIZE_BITS_DATA_PATH));
       this.Registers.add(new Smask(SIZE_BITS_DATA_PATH));
       this.Registers.add(new InstructionRegister(SIZE_BITS_DATA_PATH));
       this.Registers.add(new ProgramCounter(SIZE_BITS_DATA_PATH));
       this.Registers.add(new RegisterMinusOne(SIZE_BITS_DATA_PATH));
       this.Registers.add(new RegisterOne(SIZE_BITS_DATA_PATH));
       this.Registers.add(new RegisterZero(SIZE_BITS_DATA_PATH));
       this.Registers.add(new StackPointer(SIZE_BITS_DATA_PATH));
       this.Registers.add(new TemporaryInstructionRegister(SIZE_BITS_DATA_PATH));
       
       // aqui adicionamos os registradores utilizáveis na microarquitetura
       // A, B, C, D, E e F
       for (int i = 0; i < USABLE_REGISTERS; i ++) {
           this.Registers.add(new Register(SIZE_BITS_DATA_PATH));
       }
    }
    
    public void reset_all_registers() {
        this.Registers.get(0).resetRegister(); // reseta Accumulator
        this.Registers.get(3).resetRegister(); // reseta Instruction Register 
        this.Registers.get(4).resetRegister(); // reseta ProgramCounter
        this.Registers.set(8, new StackPointer(SIZE_BITS_DATA_PATH)); // reseta StackPointer
        this.Registers.get(9).resetRegister();
        
        // resetando os registradores utilizáveis na microarquitetura
        for (int i = USABLE_REGISTERS_BEGIN_AT; i < USABLE_REGISTERS; i++) {
            this.Registers.get(i).resetRegister();
        }
    }
}
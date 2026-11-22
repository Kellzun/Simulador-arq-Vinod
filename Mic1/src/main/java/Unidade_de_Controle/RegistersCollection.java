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
    private static final int SIZE_BITS_DATA_PATH = 16;
    private static final int USABLE_REGISTERS = 6;
   
    // Índices = números usados nos campos A, B e C do MIR
    public static final int PC = 0;
    public static final int AC = 1;
    public static final int SP = 2;
    public static final int IR = 3;
    public static final int TIR = 4;
    public static final int ZERO = 5;
    public static final int ONE = 6;
    public static final int MINUS_ONE = 7;
    public static final int AMASK = 8;
    public static final int SMASK = 9;
    public static final int USABLE_REGISTERS_BEGIN_AT = 10; // a, b, c, d, e, f
    
    private final ArrayList<Register> registers = new ArrayList<>();
    // essa classe guardará TODOS os registradores da via de dados com exceção do MBR e do MAr
    public RegistersCollection() {
       registers.add(new ProgramCounter(SIZE_BITS_DATA_PATH));               // 0
       registers.add(new Accumulator(SIZE_BITS_DATA_PATH));                  // 1
       registers.add(new StackPointer(SIZE_BITS_DATA_PATH));                 // 2
       registers.add(new InstructionRegister(SIZE_BITS_DATA_PATH));          // 3
       registers.add(new TemporaryInstructionRegister(SIZE_BITS_DATA_PATH)); // 4
       registers.add(new RegisterZero(SIZE_BITS_DATA_PATH));                 // 5
       registers.add(new RegisterOne(SIZE_BITS_DATA_PATH));                  // 6
       registers.add(new RegisterMinusOne(SIZE_BITS_DATA_PATH));             // 7
       registers.add(new Amask(SIZE_BITS_DATA_PATH));                        // 8
       registers.add(new Smask(SIZE_BITS_DATA_PATH));                        // 9
       // aqui adicionamos os registradores utilizáveis na microarquitetura
       // A, B, C, D, E e F
       for (int i = 0; i < USABLE_REGISTERS; i ++) {
           this.registers.add(new Register(SIZE_BITS_DATA_PATH));
       }
    }
    
    public void reset_all_registers() {
        registers.get(PC).resetRegister();
        registers.get(AC).resetRegister();
        registers.set(SP, new StackPointer(SIZE_BITS_DATA_PATH)); // volta ao valor inicial do SP
        registers.get(IR).resetRegister();
        registers.get(TIR).resetRegister();
        
        // resetando os registradores utilizáveis na microarquitetura
        for (int i = USABLE_REGISTERS_BEGIN_AT; i < USABLE_REGISTERS_BEGIN_AT + USABLE_REGISTERS; i++) {
            registers.get(i).resetRegister();
        }
    }
    
    public ArrayList<Register> getRegisters() {
        return this.registers;
    }
    
    // usado no subciclo 2 e no 4
    public Register getRegister(int index) {
        return registers.get(index);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Montador;

import Viadedados.MainMemory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author arthu
 */
public class Assembler {
    private static final Map<String, Integer> OPCODES_SIMPLE = new HashMap<>();
    private static final Map<String, Integer> OPCODES_F_GROUP= new HashMap<>();
    
    static {
        OPCODES_SIMPLE.put("LODD", 0b0000);
        OPCODES_SIMPLE.put("STOD", 0b0001);
        OPCODES_SIMPLE.put("ADDD", 0b0010);
        OPCODES_SIMPLE.put("SUBD", 0b0011);
        OPCODES_SIMPLE.put("JPOS", 0b0100);
        OPCODES_SIMPLE.put("JZER", 0b0101);
        OPCODES_SIMPLE.put("JUMP", 0b0110);
        OPCODES_SIMPLE.put("LOCO", 0b0111);
        OPCODES_SIMPLE.put("LODL", 0b1000);
        OPCODES_SIMPLE.put("STOL", 0b1001);
        OPCODES_SIMPLE.put("ADDL", 0b1010);
        OPCODES_SIMPLE.put("SUBL", 0b1011);
        OPCODES_SIMPLE.put("JNEG", 0b1100);
        OPCODES_SIMPLE.put("JNZE", 0b1101);
        OPCODES_SIMPLE.put("CALL", 0b1110);

        OPCODES_F_GROUP.put("PSHI", 0b0000);
        OPCODES_F_GROUP.put("POPI", 0b0010);
        OPCODES_F_GROUP.put("PUSH", 0b0100);
        OPCODES_F_GROUP.put("POP",  0b0110);
        OPCODES_F_GROUP.put("RETN", 0b1000);
        OPCODES_F_GROUP.put("SWAP", 0b1010);
        OPCODES_F_GROUP.put("INSP", 0b1100);
        OPCODES_F_GROUP.put("DESP", 0b1110);
    }
    
    // Converte UMA line de assembly em uma word de 16 bits
    public static int[] assembleLine(String line) {
        line = line.split("//")[0].trim(); // remove comentário, se houver
        if (line.isEmpty()) return null;

        String[] parts = line.split("\\s+");
        String mnem = parts[0].toUpperCase();

        if (OPCODES_SIMPLE.containsKey(mnem)) {
            int opcode = OPCODES_SIMPLE.get(mnem);
            int operand = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            int word = (opcode << 12) | (operand & 0x0FFF);
            return toBits(word, 16);
        }

        if (OPCODES_F_GROUP.containsKey(mnem)) {
            int sub = OPCODES_F_GROUP.get(mnem);
            int operand = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
            int word = (0b1111 << 12) | (sub << 8) | (operand & 0x00FF);
            return toBits(word, 16);
        }

        throw new IllegalArgumentException("Instrução desconhecida: " + mnem);
    }
    
    // Monta um programa inteiro: lista de lines -> grava na memória a partir do endereço 0
    public static void assembleProgram(List<String> lines, MainMemory memory) {
        int address = 0;
        for (String line : lines) {
            int[] word = assembleLine(line);
            if (word == null) continue; // line vazia/comentário
            memory.write_data(word, address);
            address++;
        }
    }

    private static int[] toBits(int value, int size) {
        int[] bits = new int[size];
        for (int i = 0; i < size; i++) bits[i] = (value >> (size - 1 - i)) & 1;
        return bits;
    }
}

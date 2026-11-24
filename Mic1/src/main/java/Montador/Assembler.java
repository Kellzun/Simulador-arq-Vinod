package Montador;

import Viadedados.MainMemory;
import java.util.*;

/**
 * Montador de duas passagens: aceita labels no formato "inicio:".
 * Labels ocupam o endereço da próxima instrução; saltos usam endereço absoluto.
 */
public class Assembler {
    private static final Map<String, Integer> OPCODES_SIMPLE = new HashMap<>();
    private static final Map<String, Integer> OPCODES_F_GROUP = new HashMap<>();
    public static final int HALT_WORD = 0xFF00; // grupo F, subopcode reservado 0001
    private static final int VARIABLE_START_ADDRESS = 3000;
    private static final int VARIABLE_END_ADDRESS = 4095;
    
    static {
        OPCODES_SIMPLE.put("LODD", 0); OPCODES_SIMPLE.put("STOD", 1);
        OPCODES_SIMPLE.put("ADDD", 2); OPCODES_SIMPLE.put("SUBD", 3);
        OPCODES_SIMPLE.put("JPOS", 4); OPCODES_SIMPLE.put("JZER", 5);
        OPCODES_SIMPLE.put("JUMP", 6); OPCODES_SIMPLE.put("LOCO", 7);
        OPCODES_SIMPLE.put("LODL", 8); OPCODES_SIMPLE.put("STOL", 9);
        OPCODES_SIMPLE.put("ADDL", 10); OPCODES_SIMPLE.put("SUBL", 11);
        OPCODES_SIMPLE.put("JNEG", 12); OPCODES_SIMPLE.put("JNZE", 13);
        OPCODES_SIMPLE.put("CALL", 14);
        OPCODES_F_GROUP.put("PSHI", 0); OPCODES_F_GROUP.put("POPI", 2);
        OPCODES_F_GROUP.put("PUSH", 4); OPCODES_F_GROUP.put("POP", 6);
        OPCODES_F_GROUP.put("RETN", 8); OPCODES_F_GROUP.put("SWAP", 10);
        OPCODES_F_GROUP.put("INSP", 12); OPCODES_F_GROUP.put("DESP", 14);
    }
    private static boolean isDirectMemoryInstruction(String mnemonic) {
        switch (mnemonic.toUpperCase(Locale.ROOT)) {
            case "LODD":
            case "STOD":
            case "ADDD":
            case "SUBD":
                return true;
            default:
                return false;
        }
    }
    private static class ParsedLine {
        final int sourceLine, address;
        final String mnemonic, operand;
        ParsedLine(int sourceLine, int address, String mnemonic, String operand) {
            this.sourceLine=sourceLine; this.address=address; this.mnemonic=mnemonic; this.operand=operand;
        }
    }

    public static int[] assembleLine(String line) {
        String clean = stripComment(line).trim();
        if (clean.isEmpty()) return null;
        String[] parts = clean.split("\\s+");
        return encode(parts[0], parts.length > 1 ? parts[1] : null, Collections.emptyMap(), 0);
    }

    public static void assembleProgram(List<String> lines, MainMemory memory) {
        Map<String,Integer> labels = new LinkedHashMap<>();
        List<ParsedLine> parsed = new ArrayList<>();
        int address=0;
        // Tabela de símbolos compartilhada por labels (código) e variáveis (dados).
        // Sintaxe de variável: VAR nome endereço  (ex.: VAR var1 100)
        // Primeira passagem: coleta símbolos e calcula os endereços das instruções.
        for (int i=0; i<lines.size(); i++) {
            String text=stripComment(lines.get(i)).trim();
            if (text.isEmpty()) continue;
            int colon=text.indexOf(':');
            if (colon >= 0) {
                String label=text.substring(0,colon).trim();
                if (!label.matches("[A-Za-z_][A-Za-z0-9_]*"))
                    throw error(i, "label inválida: " + label);
                String key=label.toUpperCase(Locale.ROOT);
                if (labels.putIfAbsent(key,address)!=null) throw error(i, "label duplicada: " + label);
                text=text.substring(colon+1).trim();
                if (text.isEmpty()) continue;
            }
            String[] p=text.split("\\s+");
            if (p[0].equalsIgnoreCase("VAR")) {
                if (p.length != 3)
                    throw error(i, "sintaxe de variável inválida. Use: VAR nome endereço");
                String name=p[1];
                if (!name.matches("[A-Za-z_][A-Za-z0-9_]*"))
                    throw error(i, "nome de variável inválido: " + name);
                int dataAddress;
                try {
                    dataAddress=parseNumber(p[2]);
                } catch (NumberFormatException ex) {
                    throw error(i, "endereço inválido para a variável " + name + ": " + p[2]);
                }
                if (dataAddress < 0 || dataAddress > 4095)
                    throw error(i, "endereço da variável deve estar entre 0 e 4095.");
                String key=name.toUpperCase(Locale.ROOT);
                if (labels.putIfAbsent(key,dataAddress)!=null)
                    throw error(i, "símbolo duplicado: " + name);
                continue; // declaração VAR não ocupa posição no programa.
            }
            String[] instruction=text.split("\\s+",2);
            parsed.add(new ParsedLine(i+1,address,instruction[0],
                    instruction.length>1?instruction[1].trim():null));
            address++;
        }
        if (address > 4096) throw new IllegalArgumentException("Programa excede os 4096 endereços da memória.");
        
        // Segunda passagem: resolve operandos e grava as palavras.
        int nextVariableAddress = Math.max(VARIABLE_START_ADDRESS, address);

        for (ParsedLine p : parsed) {
            try {
                String mnemonic = p.mnemonic.toUpperCase(Locale.ROOT);

                if (isDirectMemoryInstruction(mnemonic)
                        && p.operand != null) {

                    String variableName = p.operand.trim();
                    String key = variableName.toUpperCase(Locale.ROOT);

                    boolean isSymbol = variableName.matches(
                            "[A-Za-z_][A-Za-z0-9_]*"
                    );

                    if (isSymbol && !labels.containsKey(key)) {

                        // Procura um endereço livre.
                        while (nextVariableAddress <= VARIABLE_END_ADDRESS
                                && labels.containsValue(nextVariableAddress)) {
                            nextVariableAddress++;
                        }

                        if (nextVariableAddress > VARIABLE_END_ADDRESS) {
                            throw new IllegalArgumentException(
                                    "Não há espaço disponível para novas variáveis."
                            );
                        }

                        labels.put(key, nextVariableAddress);
                        nextVariableAddress++;
                    }
                }

                int[] word = encode(
                        p.mnemonic,
                        p.operand,
                        labels,
                        p.address
                );

                memory.write_data(word, p.address);

            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(
                        "Linha " + p.sourceLine + ": " + ex.getMessage(),
                        ex
                );
            }
        }
    }

    private static int[] encode(String mnemonic, String operandText, Map<String,Integer> labels, int address) {
        String m=mnemonic.toUpperCase(Locale.ROOT);
        if (m.equals("HALT")) {
            if (operandText!=null && !operandText.isEmpty()) throw new IllegalArgumentException("HALT não recebe operando.");
            return toBits(HALT_WORD,16);
        }
        int operand=resolveOperand(operandText,labels);
        if (OPCODES_SIMPLE.containsKey(m)) {
            if (operand < 0 || operand > 0xFFF) throw new IllegalArgumentException("operando fora do intervalo 0..4095.");
            return toBits((OPCODES_SIMPLE.get(m)<<12)|operand,16);
        }
        if (OPCODES_F_GROUP.containsKey(m)) {
            if (operand < 0 || operand > 0xFF) throw new IllegalArgumentException("operando fora do intervalo 0..255.");
            return toBits((0xF<<12)|(OPCODES_F_GROUP.get(m)<<8)|operand,16);
        }
        throw new IllegalArgumentException("Instrução desconhecida: " + mnemonic);
    }

    private static int resolveOperand(String operand, Map<String,Integer> labels) {
        if (operand==null || operand.isEmpty()) return 0;
        String token=operand.trim();
        Integer label=labels.get(token.toUpperCase(Locale.ROOT));
        if (label!=null) return label;
        try {
            return parseNumber(token);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("operando/label/variável não reconhecido: " + token);
        }
    }

    private static int parseNumber(String token) {
        if (token.startsWith("0x") || token.startsWith("0X"))
            return Integer.parseInt(token.substring(2),16);
        return Integer.parseInt(token);
    }

    private static String stripComment(String s) {
        int a=s.indexOf("//"), b=s.indexOf(';');
        int end=(a<0? s.length():a);
        if (b>=0) end=Math.min(end,b);
        return s.substring(0,end);
    }
    private static IllegalArgumentException error(int line,String message) {
        return new IllegalArgumentException("Linha " +(line+1)+": "+message);
    }
    private static int[] toBits(int value,int size) {
        int[] bits=new int[size];
        for(int i=0;i<size;i++) bits[i]=(value>>(size-1-i))&1;
        return bits;
    }
}

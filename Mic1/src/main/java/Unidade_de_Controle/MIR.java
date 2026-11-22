/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Unidade_de_Controle;

import Viadedados.Registers.Register;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author arthu
 */
public class MIR extends Register{
    private final int WORD_SIZE = 32;
    
    public MIR(int size_bits) { // size_bits = 32
        super(size_bits);
        for (int i = 0; i < WORD_SIZE;i++) {
            this.register[i] = 0;
        }
    }
    
    // FORMATO DE UMA MICROINSTRUÇÃO (MIC-1)
    //      AMUX | COND | ULA | DESL | MBR | MAR | RD | WR | En C | Bar. C | Bar. B | Bar. A | ADDR
    // bits: 1   |  2   |  2  |  2   |  1  |  1  | 1  | 1  |   1  |   4    |   4    |   4    |  8
    
    public List<Integer> retrieve_camp(int start_bit, int end_bit) {
        List<Integer> required_camp = new ArrayList<>();
        
        for (int i = start_bit; i < end_bit; i++) {//pegando o campo que a gente quer
            required_camp.add(this.register[i]);
        }
        return required_camp;
    }
}

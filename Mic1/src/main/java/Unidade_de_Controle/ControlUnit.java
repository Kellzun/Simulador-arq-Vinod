/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Unidade_de_Controle;

import Viadedados.*;
import Viadedados.Registers.*;

/**
 *
 * @author arthu
 */
public class ControlUnit {
   // nessa classe juntaremos tudo
   
   // constantes importantes
   private final int NUMBER_OF_ADDRESSES = 4096;
   private final int SIZE_MAR = 12;
   private final int SIZE_CONTROL_STORE = 256;
   private final int WORD = 16;
   
   // Posições dos campos no MIR (32 bits, índice 0 = MSB)
   private static final int AMUX = 0; // 1 bit
   private static final int COND = 1,  COND_LENGTH = 2;
   private static final int ALU  = 3,  ALU_LENGTH  = 2;
   private static final int SH   = 5,  SH_LENGTH   = 2;
   private static final int F_MBR = 7;
   private static final int F_MAR = 8;
   private static final int RD = 9;
   private static final int WR = 10;
   private static final int ENC = 11;
   private static final int C = 12, B = 16, A = 20, REG_LENGTH = 4;
   private static final int ADDR = 24, ADDR_LENGTH = 8;
   
   
   // Componentes utilizados na Unidade de Controle
   private final MPC mpc = new MPC(8);
   private final ControlStore controlStore = new ControlStore(SIZE_CONTROL_STORE);
   private final MUX mmux = new MUX(2, 8, 1);
   private final MUX amux = new MUX(2, WORD, 1);
   private final FlowControl flowControl = new FlowControl();
   private final ALU ula = new ALU();
   private final Shifter shifter = new Shifter();
   private final MAR mar = new MAR(SIZE_MAR);
   private final MBR mbr = new MBR(WORD);
   private final MainMemory memory= new MainMemory(NUMBER_OF_ADDRESSES);
   
   public void CycleExecution() {
       subcycle01();
       subcycle02();
       subcycle03();
       subcycle04();
   }
   
   private void subcycle01() {
       
   }
   private void subcycle02() {
       
   }
   private void subcycle03() {
       
   }
   private void subcycle04() {
       
   }
   
}

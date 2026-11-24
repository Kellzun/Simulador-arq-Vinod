/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Unidade_de_Controle;

import Viadedados.*;
import Viadedados.Registers.*;
import Viadedados.CacheMemory;

/**
 *
 * @author arthu
 */
public class ControlUnit {
   // nessa classe juntaremos tudo
   
   // constantes importantes
   private static final int NUMBER_OF_ADDRESSES = 4096;
   private static final int MAR_SIZE = 12;
   private static final int ADDRESS_SIZE = 8;
   private static final int SIZE_CONTROL_STORE = 256;
   private static final int MIR_SIZE = 32;
   private static final int WORD = 16;
   
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
   
   
   // Componentes
   private final MPC mpc = new MPC(ADDRESS_SIZE);
   private final ControlStore controlStore = new ControlStore(SIZE_CONTROL_STORE);
   private final MUX mmux = new MUX(2, ADDRESS_SIZE, 1);
   private final MUX amux = new MUX(2, WORD, 1);
   private final FlowControl flowControl = new FlowControl();
   private final ALU alu = new ALU();
   private final Shifter shifter = new Shifter();
   private final MIR mir = new MIR(MIR_SIZE);
   private final MAR mar = new MAR(MAR_SIZE);
   private final MBR mbr = new MBR(WORD);
   private final MainMemory memory = new MainMemory(NUMBER_OF_ADDRESSES);
   private final CacheMemory cache = new CacheMemory();
   private int[] busC = new int[WORD]; // barramento C
   
   // controle RD e WR
   private boolean read_pending = false;
   private boolean write_pending = false;
   private int lastMicroAddress = -1;
   private String lastMicroInstruction = "";
   private boolean halted = false;
   
   // latches A e B
   private final Latch latchA = new Latch();
   private final Latch latchB = new Latch();
   
   // registradores
   private final RegistersCollection registers = new RegistersCollection();
   
   public void cycleExecution() {
       
    // Captura o endereço da microinstrução atual
    lastMicroAddress = bitsToInt(mpc.getRegister());

    // Busca o texto correspondente
    if (lastMicroAddress >= 0 &&
        lastMicroAddress < MicroInstructions.microInstructionsAsStrings.length) {

        lastMicroInstruction =
            MicroInstructions.microInstructionsAsStrings[lastMicroAddress];

    } else {
        lastMicroInstruction =
            "Microinstrução não encontrada: " + lastMicroAddress;
    }

    // Executa os quatro subciclos
       subcycle01();
       subcycle02();
       subcycle03();
       subcycle04();
   }
   
   private void subcycle01() {
       // mpc, memoria de controle e mir
       // mir recebe a instrucao na posicao MPC da memoria de controle
       mir.setRegister(controlStore.getInstruction(mpc.getRegister()));
   }
   private void subcycle02() {
       // vamos jogar no latch A e no latch B lendo MIR
       // posicao latchA = 20 -> 23 | posicao latchB = 16 -> 19
       int[] fieldB, fieldA;
       int operationA = 0, operationB = 0; // conversao para decimal
       
       fieldB = mir.retrieve_camp(16, 20); // barramento B
       fieldA = mir.retrieve_camp(20, 24); // barramento A
       
       // transformamos o codigo da operação para procurar o registrador
       // conversão de binário para decimal
       for (int i = 0; i < fieldB.length; i++) {
           operationB = (operationB << 1) | fieldB[i];
       }
       for (int i = 0; i < fieldA.length; i++) {
           operationA = (operationA << 1) | fieldA[i];
       }
       
       //jogando nos latches os registradores nas posições opA e opB
       latchA.setValue_stored(registers.getRegister(operationA).getRegister());
       latchB.setValue_stored(registers.getRegister(operationB).getRegister());
       
   }
   private void subcycle03() {
       amux.setInput(0, latchA.getValue_stored()); amux.setInput(1, mbr.write());
       
       int[] Amuxbit = mir.retrieve_camp(AMUX, AMUX + 1);
       amux.setControl(Amuxbit);
       
       // transformamos o codigo da operação para ULA
       // conversão de binário para decimal
       int[] ULAbit = mir.retrieve_camp(ALU, ALU + ALU_LENGTH); // pedaço da ula
       int[] SHbit = mir.retrieve_camp(SH, SH + SH_LENGTH); // pedaço do deslocamento(shifter)
       
       int ULAint = 0, SHint = 0;
       for (int i = 0; i < ULAbit.length; i++) {
           ULAint = (ULAint << 1) | ULAbit[i];
       }
       for (int i = 0; i < SHbit.length; i++) {
           SHint = (SHint << 1) | SHbit[i];
       }
       
       int[] ULAoutput = alu.operateALU(amux.getOutput(), latchB.getValue_stored(), ULAint);
       busC = shifter.apply_operation(ULAoutput.clone(), SHint);
       
       //pegando o bit do MAR no MIR
       int[] MARbit = mir.retrieve_camp(F_MAR, F_MAR + 1);
       
       if (MARbit[0] == 1) {
            mar.load(latchB.getValue_stored());
       }
   }
   private void subcycle04() {
       // ENC
       int[] ENCbit = mir.retrieve_camp(ENC, ENC + 1);
       if (ENCbit[0] == 1) {
            // conversão de binário para decimal do campo C
           int[] fieldC = mir.retrieve_camp(C, C + REG_LENGTH);
           int indexC = 0;
           for (int i = 0; i < fieldC.length; i++) {
                indexC = (indexC << 1) | fieldC[i];
           }
           registers.getRegister(indexC).setRegister(busC);
           // HALT é reconhecido quando a palavra da instrução chega ao IR.
           if (indexC == RegistersCollection.IR && bitsToInt(busC) == 0xFF00) {
               halted = true;
           }
       }
       
       int[] MBRbit = mir.retrieve_camp(F_MBR, F_MBR + 1);
       if (MBRbit[0] == 1) {
           mbr.setRegister(busC);
       }
       
       // tratar RD e WR
       int[] rd = mir.retrieve_camp(RD, RD + 1);
       int[] wr = mir.retrieve_camp(WR, WR + 1);
       
       // convertendo o MAR para inteiro
       int[] marAddress = mar.getRegister();
       int marAddressint = 0;
       for (int i = 0; i < marAddress.length; i++) {
           marAddressint = (marAddressint << 1) | marAddress[i];
       }
       
       if (rd[0] == 1) {
           if (read_pending) {
               int[] value = cache.read(marAddressint, memory);
               //mbr.setRegister(memory.read_Memory_storage(marAddressint));
               mbr.setRegister(value);
               if (value != null) {
                   mbr.setRegister(value);
               }
               
               read_pending = false;
           } else {
               read_pending = true;
           }
       } else {
           read_pending = false;
       }
       
       if (wr[0] == 1) {
           if (write_pending) {
               cache.write(marAddressint, mbr.getRegister(), memory);
               //memory.write_data(mbr.getRegister(), marAddressint);
               write_pending = false;
           } else {
               write_pending = true;
           }
       } else {
           write_pending = false;
       }
       
       // proximo MPC
       int[] cond = mir.retrieve_camp(COND, COND + COND_LENGTH);
       int[] addr = mir.retrieve_camp(ADDR, ADDR + ADDR_LENGTH);
       int m = flowControl.calculateM(cond, alu.getN(), alu.getZ());
       mmux.setInput(0, mpc.incrementMPC());
       mmux.setInput(1, addr);
       mmux.setControl(new int[]{m});
       mpc.setRegister(mmux.getOutput());
       
   }
   
   public MPC getMpc() { return mpc; }
   public MBR getMbr() { return mbr; }
   public MAR getMar() { return mar; }
   public RegistersCollection getRegisters() { return registers; }
   public MainMemory getMemory() { return memory; }
   public CacheMemory getCache() { return cache;}
   public MIR getMir() { return mir; }
   public int getLastMicroAddress() {
       return lastMicroAddress;
   }
   public String getLastMicroInstruction() {
       return lastMicroInstruction;
   }
   
   public boolean isHalted() { return halted; }
   private int bitsToInt(int[] bits) {
       int value=0;
       for (int bit : bits) value=(value<<1)|(bit&1);
       return value;
   }
   
   // zera tudo antes de carregar um novo programa
   public void resetAll() {
       memory.reset_memory_storage();
 
       mpc.reset_MPC();
       mbr.resetRegister();
       mar.resetRegister();
       registers.reset_all_registers();
       
       read_pending = false;
       write_pending = false;
       halted = false;
       
       lastMicroAddress = -1;
       lastMicroInstruction = "";
   
       cache.clear();
   }
}

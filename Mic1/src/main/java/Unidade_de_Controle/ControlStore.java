/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Unidade_de_Controle;

import Viadedados.Registers.Memory;

/**
 *
 * @author arthu
 */
public class ControlStore extends Memory{
    // numero de microinstruções realmente utilizadas
    private final int NUM_MICROINSTRUCTION = 78;
    
    // definido tamanho igual a 32 bits (padrão de microinstrução)
    public ControlStore(int size) {
        super(size);
        this.memory_storage.add(new int[]{0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //0
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0}); //1
        this.memory_storage.add(new int[]{1,0,1,1,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0}); //2
        this.memory_storage.add(new int[]{0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,1,0,0,1,1,0,0,0,1,0,0,1,1}); //3
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,1}); //4
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,1}); //5
        this.memory_storage.add(new int[]{0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0}); //6
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //7
        this.memory_storage.add(new int[]{1,1,1,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //8
        this.memory_storage.add(new int[]{0,0,0,1,0,0,0,1,1,0,1,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0}); //9
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //10
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,1}); //11
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0}); //12
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //13
        this.memory_storage.add(new int[]{1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}); //14
        this.memory_storage.add(new int[]{0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0}); //15
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,1,0,0,0,1,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0}); //16
        this.memory_storage.add(new int[]{1,0,0,1,1,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //17
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0}); //18
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,0,0,1}); //19
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,1,1,1}); //20
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}); //21
        this.memory_storage.add(new int[]{0,1,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0}); //22
        this.memory_storage.add(new int[]{0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0}); //23
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //24
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,0,1,1}); //25
        this.memory_storage.add(new int[]{0,1,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0}); //26
        this.memory_storage.add(new int[]{0,1,1,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0}); //27
        this.memory_storage.add(new int[]{0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,1,0,0,1,1,0,0,1,0,1,0,0,0}); //28
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,1}); //29
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1}); //30
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0}); //31
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,1,1}); //32
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0}); //33
        this.memory_storage.add(new int[]{0,1,1,1,0,0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,1,0,1,0}); //34
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1,1,0}); //35
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0}); //36
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,1,1,0,1}); //37
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,0,0,1,0,0,0,0,0,0,0,0,0}); //38
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0}); //39
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,1,1,0}); //40
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,1,1,0,0}); //41
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0}); //42
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //43
        this.memory_storage.add(new int[]{0,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}); //44
        this.memory_storage.add(new int[]{0,1,1,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0}); //45
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,1,0}); //46
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0}); //47
        this.memory_storage.add(new int[]{0,0,0,1,0,0,0,1,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0}); //48
        this.memory_storage.add(new int[]{0,1,1,0,1,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0}); //49
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1}); //50
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,0,1,1}); //51
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,0,0,0}); //52
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0}); //53
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,1,0,0,1,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0}); //54
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,1,0}); //55
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,1,1,0,1,0,0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0}); //56
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //57
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,1,0}); //58
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,1,1,1,0}); //59
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0}); //60
        this.memory_storage.add(new int[]{0,1,1,1,0,0,0,1,1,0,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,1,0}); //61
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,1,1,0,1,0,0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0}); //62
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //63
        this.memory_storage.add(new int[]{1,1,1,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //64
        this.memory_storage.add(new int[]{0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,1}); //65
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,1,0}); //66
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,1,1,0,1,0,0,1,0,0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0}); //67
        this.memory_storage.add(new int[]{0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //68
        this.memory_storage.add(new int[]{1,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}); //69
        this.memory_storage.add(new int[]{0,0,0,1,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}); //70
        this.memory_storage.add(new int[]{0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0}); //71
        this.memory_storage.add(new int[]{0,1,1,1,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0}); //72
        this.memory_storage.add(new int[]{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,1,1,0,0}); //73
        this.memory_storage.add(new int[]{0,0,0,0,1,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,1,0,0,1,0,0,0,0,0,0,0,0}); //74
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,0}); //75
        this.memory_storage.add(new int[]{0,0,0,0,1,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,1,0,0,1,0,0,0,0,0,0,0,0}); //76
        this.memory_storage.add(new int[]{0,0,0,1,1,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0}); //77
        this.memory_storage.add(new int[]{0,1,1,0,0,0,0,0,0,0,0,1,1,0,1,0,1,0,1,0,0,1,1,0,0,1,0,0,1,0,1,1}); //78
    }
    
    public int[] getInstruction(int[] MPC) {
        
        // transformamos o codigo da operacao para procurar na memória de controle
        int operation_decimal= 0;
        for (int i = 0; i < MPC.length; i++) {
            // conversão de binário para decimal
            operation_decimal = (operation_decimal << 1) | MPC[i];
        }
        
        return this.memory_storage.get(operation_decimal);
    }
}

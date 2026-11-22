/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viadedados;

import Viadedados.Registers.Memory;

/**
 *
 * @author arthu
 */
public class MainMemory extends Memory{
    private int[] null_address= new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; // tamanho = 16
    
    public MainMemory(int size) {
        super(size);
        for (int i=0; i<size; i++) {
            memory_storage.add(this.null_address);
        }
    }
    public void reset_memory_storage() {
        for (int i=0;i<memory_storage.size(); i++) {
            // a funcao set automaticamente escreve encima da posicao que queremos substituir
            this.memory_storage.set(i, this.null_address);
        }
    }
    
    public int get_position_address(int[] address) {
        for (int i=0;i<this.memory_storage.size();i++) {
            if (this.memory_storage.get(i) == address) return i;
        }
        // endereco nao encontrado
        return -1;
    }
    public int[] read_address(int [] address) {
        int position = get_position_address(address);
        if (position == -1) System.out.println("endereco nao foi encontrado");
        return this.memory_storage.get(position);
    }
    
    public void write_data(int[] data, int position) {
        this.memory_storage.set(position, data); 
    }
    
}

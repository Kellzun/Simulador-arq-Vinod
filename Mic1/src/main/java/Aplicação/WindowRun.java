/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Aplicação;

import Unidade_de_Controle.ControlUnit;
import Montador.Assembler;
import Montador.Conversion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author arthu
 */
public class WindowRun extends JFrame {
    private final ControlUnit cu = new ControlUnit();
    private javax.swing.Timer timer;

    private final JTextArea codeArea = new JTextArea(20, 30);
    private final JLabel pcLabel = new JLabel();
    private final JLabel acLabel = new JLabel();
    private final JLabel spLabel = new JLabel();
    private final JLabel irLabel = new JLabel();
    private final JLabel mbrLabel = new JLabel();
    private final JLabel marLabel = new JLabel();
    private final JLabel mpcLabel = new JLabel();
    private final JLabel statusLabel = new JLabel(" ");
    private final JSlider speedSlider = new JSlider(10, 1000, 200);

    private static final int MEMORY_PREVIEW = 20; // quantos endereços mostrar na tabela
    private final DefaultTableModel memoryModel =
            new DefaultTableModel(new Object[]{"Endereço", "Valor (hex)"}, 0);

    public WindowRun() {
        setTitle("Simulador MIC-1 / Mac-1");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        codeArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        add(new JScrollPane(codeArea), BorderLayout.WEST);

        JPanel estado = new JPanel(new GridLayout(0, 1));
        estado.add(mpcLabel);
        estado.add(pcLabel);
        estado.add(acLabel);
        estado.add(spLabel);
        estado.add(irLabel);
        estado.add(mbrLabel);
        estado.add(marLabel);
        estado.add(statusLabel);
        add(estado, BorderLayout.CENTER);

        JTable memoryTable = new JTable(memoryModel);
        add(new JScrollPane(memoryTable), BorderLayout.EAST);

        JPanel botoes = new JPanel();
        JButton carregar = new JButton("Carregar");
        JButton passo = new JButton("Passo");
        JButton rodar = new JButton("Rodar");
        JButton pausar = new JButton("Pausar");
        JButton reiniciar = new JButton("Reiniciar");
        botoes.add(carregar);
        botoes.add(passo);
        botoes.add(rodar);
        botoes.add(pausar);
        botoes.add(reiniciar);
        botoes.add(new JLabel("Velocidade:"));
        botoes.add(speedSlider);
        add(botoes, BorderLayout.SOUTH);

        carregar.addActionListener(e -> carregarPrograma());
        passo.addActionListener(e -> executarUmPasso());
        rodar.addActionListener(e -> iniciarExecucao());
        pausar.addActionListener(e -> pausarExecucao());
        reiniciar.addActionListener(e -> reiniciarExecucao());
        
        atualizarPainel();
        pack();
    }

    // ---------- Carregar ----------

    private void carregarPrograma() {
        pausarExecucao();

        String texto = codeArea.getText();
        List<String> linhas = new ArrayList<>();
        for (String linha : texto.split("\n")) {
            linhas.add(linha);
        }

        try {
            cu.resetAll();
            Assembler.assembleProgram(linhas, cu.getMemory());
            statusLabel.setText("Programa carregado.");
        } catch (IllegalArgumentException ex) {
            statusLabel.setText("Erro ao montar: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro de montagem", JOptionPane.ERROR_MESSAGE);
            return;
        }

        atualizarPainel();
    }

    // ---------- Execução ----------

    private void executarUmPasso() {
        cu.cycleExecution();
        atualizarPainel();
    }

    private void iniciarExecucao() {
        if (timer != null && timer.isRunning()) return;

        int intervalo = speedSlider.getMaximum() - speedSlider.getValue();
        timer = new javax.swing.Timer(intervalo, e -> {
            cu.cycleExecution();
            atualizarPainel();
        });
        timer.start();
        statusLabel.setText("Rodando...");
    }

    private void pausarExecucao() {
        if (timer != null) {
            timer.stop();
        }
        statusLabel.setText("Pausado.");
    }
    
    // Volta o estado da execução ao início (MPC=0, registradores zerados),
    // SEM reler o código: a memória continua com o programa já montado,
    // então a execução recomeça do zero sobre o mesmo programa.
    private void reiniciarExecucao() {
        pausarExecucao();
        cu.resetAll();
        atualizarPainel();
        statusLabel.setText("Reiniciado.");
    }
    
    // ---------- Atualização da tela ----------

    private void atualizarPainel() {
        int pc  = Conversion.binaryToDecimal(cu.getRegisters().getRegister(0).getRegister()); // PC
        int ac  = Conversion.binaryToDecimal(cu.getRegisters().getRegister(1).getRegister()); // AC
        int sp  = Conversion.binaryToDecimal(cu.getRegisters().getRegister(2).getRegister()); // SP
        int ir  = Conversion.binaryToDecimal(cu.getRegisters().getRegister(3).getRegister()); // IR
        int mbr = Conversion.binaryToDecimal(cu.getMbr().getRegister());
        int mar = Conversion.binaryToDecimal(cu.getMar().getRegister());
        int mpc = Conversion.binaryToDecimal(cu.getMpc().getRegister());

        mpcLabel.setText("MPC: " + mpc);
        pcLabel.setText("PC: " + pc);
        acLabel.setText("AC: " + ac);
        spLabel.setText("SP: " + sp);
        irLabel.setText(String.format("IR: 0x%04X", ir));
        mbrLabel.setText(String.format("MBR: 0x%04X", mbr));
        marLabel.setText("MAR: " + mar);

        atualizarTabelaMemoria();
    }

    private void atualizarTabelaMemoria() {
        memoryModel.setRowCount(0);
        for (int endereco = 0; endereco < MEMORY_PREVIEW; endereco++) {
            int[] palavra = cu.getMemory().read_Memory_storage(endereco);
            int valor = Conversion.binaryToDecimal(palavra);
            memoryModel.addRow(new Object[]{endereco, String.format("0x%04X", valor)});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WindowRun().setVisible(true));
    }
}

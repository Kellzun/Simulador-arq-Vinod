package Aplicação;

import Unidade_de_Controle.ControlUnit;
import Unidade_de_Controle.RegistersCollection;
import Montador.Assembler;
import Montador.Conversion;
import Viadedados.CacheMemory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.TitledBorder;

public class WindowRun extends JFrame {
    private final ControlUnit cu = new ControlUnit();
    private javax.swing.Timer timer;
    private final JTextArea codeArea = new JTextArea(
        "LOCO 5\nSTOD 100\nLOCO 0\nADDD 100\nHALT", 16, 34);
    private final JTextArea historyArea = new JTextArea();
    private final JLabel statusLabel = new JLabel("Pronto — monte um programa para começar.");
    private final JLabel cyclesLabel = new JLabel("Ciclos: 0");
    private final JLabel timeLabel = new JLabel("Tempo: 0 ms");
    private final JSlider speedSlider = new JSlider(1, 1000, 200);
    
    private final DefaultTableModel memoryModel = new DefaultTableModel(
        new Object[]{"Endereço","Binário (16 bits)","Decimal","Hex"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final DefaultTableModel cacheModel = new DefaultTableModel(
        new Object[]{"Linha", "Válida", "Endereço", "Dados (16 bits)"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }   
    };
    private final JLabel cacheHitsLabel = new JLabel("Hits: 0");
    private final JLabel cacheMissesLabel = new JLabel("Misses: 0");
    private final JLabel cacheRateLabel = new JLabel("Hit Rate: 0%");
    
    private final DefaultTableModel registerModel = new DefaultTableModel(
        new Object[]{"Registrador", "Decimal", "Binário (16 bits)", "Hex"}, 0) {
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };

    private int cycles=0;
    private long startTime=0;

    public WindowRun() {
        setTitle("Simulador MIC-1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100,720));
        setSize(1380,850);
        getContentPane().setBackground(new Color(43,43,43));
        setLayout(new BorderLayout(10,10));

        JLabel title=new JLabel("Simulador MIC-1");
        title.setFont(new Font("SansSerif",Font.PLAIN,27));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(12,16,4,0));
        add(title,BorderLayout.NORTH);

        JPanel editorPanel=panel("Programa Assembly");
        codeArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
        codeArea.setTabSize(4);
        editorPanel.add(new JScrollPane(codeArea),BorderLayout.CENTER);
        JLabel help=new JLabel("Labels: nome:    |    Comentários: // ou ;    |    Saltos aceitam labels ou endereços");
        help.setForeground(new Color(210,210,210));
        editorPanel.add(help,BorderLayout.SOUTH);

        JPanel registersPanel=panel("Registradores");
        JTable regTable=new JTable(registerModel);
        regTable.setFillsViewportHeight(true);
        registersPanel.add(new JScrollPane(regTable),BorderLayout.CENTER);

        JPanel historyPanel=panel("Histórico de execução");
        historyArea.setEditable(false);
        historyArea.setFont(new Font(Font.MONOSPACED,Font.PLAIN,12));
        historyPanel.add(new JScrollPane(historyArea),BorderLayout.CENTER);

        
        JPanel cachePanel = panel("Cache");

        JTable cacheTable = new JTable(cacheModel);
        cacheTable.setFillsViewportHeight(true);
        cacheTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        cachePanel.add(new JScrollPane(cacheTable), BorderLayout.CENTER);

        JPanel cacheStats = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cacheStats.setOpaque(false);

        cacheHitsLabel.setForeground(Color.WHITE);
        cacheMissesLabel.setForeground(Color.WHITE);
        cacheRateLabel.setForeground(Color.WHITE);

        cacheStats.add(cacheHitsLabel);
        cacheStats.add(cacheMissesLabel);
        cacheStats.add(cacheRateLabel);
        
        JPanel right=new JPanel(new GridLayout(3,1,8,8));
        right.setOpaque(false);
        right.add(registersPanel); right.add(cachePanel); right.add(historyPanel);
        cachePanel.add(cacheStats, BorderLayout.SOUTH);

        JPanel memoryPanel=panel("Memória principal");
        JTable memTable=new JTable(memoryModel);
        memTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        memoryPanel.add(new JScrollPane(memTable),BorderLayout.CENTER);

        JSplitPane upper=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,editorPanel,right);
        upper.setResizeWeight(.52);
        JSplitPane main=new JSplitPane(JSplitPane.VERTICAL_SPLIT,upper,memoryPanel);
        main.setResizeWeight(.53);
        main.setBorder(null);
        add(main,BorderLayout.CENTER);

        JPanel footer=new JPanel(new BorderLayout(8,6));
        footer.setOpaque(false);
        JPanel actions=new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.setOpaque(false);
        JButton assemble=new JButton("Montar");
        JButton step=new JButton("Passo");
        JButton run=new JButton("Executar");
        JButton pause=new JButton("Pausar");
        JButton reset=new JButton("Reiniciar");
        for(JButton b:new JButton[]{assemble,step,run,pause,reset}) actions.add(b);
        JLabel interval=new JLabel("Intervalo (ms)");
        interval.setForeground(Color.WHITE);
        actions.add(interval);
        actions.add(speedSlider);
        footer.add(actions,BorderLayout.NORTH);
        JPanel info=new JPanel(new FlowLayout(FlowLayout.LEFT));
        info.setOpaque(false);
        cyclesLabel.setForeground(Color.WHITE); timeLabel.setForeground(Color.WHITE);
        statusLabel.setForeground(new Color(230,230,230));
        info.add(cyclesLabel); info.add(timeLabel); info.add(statusLabel);
        footer.add(info,BorderLayout.SOUTH);
        add(footer,BorderLayout.SOUTH);

        assemble.addActionListener(e->carregarPrograma());
        step.addActionListener(e->executarUmPasso());
        run.addActionListener(e->iniciarExecucao());
        pause.addActionListener(e->pausarExecucao("Pausado."));
        reset.addActionListener(e->reiniciarExecucao());
        atualizarPainel();
    }

    private JPanel panel(String heading) {
        JPanel p = new JPanel(new BorderLayout(6,6));
        p.setBackground(new Color(57,57,57));

        TitledBorder titulo = BorderFactory.createTitledBorder(
            BorderFactory.createEmptyBorder(5,7,7,7),
            heading
        );

        titulo.setTitleColor(new Color(0, 200, 255));

        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(125,125,125)),
            titulo
        ));

        return p;
    }
    
    private void registrarMicroinstrucao() {

        int endereco = cu.getLastMicroAddress();

        String instrucao = cu.getLastMicroInstruction();

        historyArea.append(
            String.format("\n[%03d] %s%n", endereco, instrucao)
        );

        // Mantém o histórico acompanhando a execução
        historyArea.setCaretPosition(
            historyArea.getDocument().getLength()
        );
    }
    
    private void carregarPrograma() {
        pararTimer();
        try {
            cu.resetAll();
            List<String> lines=new ArrayList<>();
            for(String line:codeArea.getText().split("\\R")) lines.add(line);
            Assembler.assembleProgram(lines,cu.getMemory());
            cycles=0; startTime=0; historyArea.setText("");
            statusLabel.setText("Programa montado com sucesso.");
            historyArea.append("Montagem concluída. PC inicial = 0");
            atualizarPainel();
        } catch(IllegalArgumentException ex) {
            statusLabel.setText("Erro de montagem: "+ex.getMessage());
            JOptionPane.showMessageDialog(this,ex.getMessage(),"Erro de montagem",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void executarUmPasso() {
        if(cu.isHalted()){statusLabel.setText("HALT — execução encerrada.");return;}
        if(startTime==0) startTime=System.currentTimeMillis();
        cu.cycleExecution(); cycles++; registrarMicroinstrucao();
        atualizarPainel();  
        if(cu.isHalted()) { pararTimer(); statusLabel.setText("HALT — processador parado."); historyArea.append("HALT detectado. Execução encerrada."); }
        else statusLabel.setText("Executando passo a passo.");
    }

    private void iniciarExecucao() {
        if(cu.isHalted()){statusLabel.setText("HALT — reinicie para executar novamente.");return;}
        if(timer!=null && timer.isRunning()) return;
        if(startTime==0) startTime=System.currentTimeMillis();
        timer=new javax.swing.Timer(Math.max(1,speedSlider.getValue()),e->{
            cu.cycleExecution(); cycles++; registrarMicroinstrucao(); atualizarPainel();
            if(cu.isHalted()){pararTimer();statusLabel.setText("HALT — processador parado.");historyArea.append("HALT detectado. Execução encerrada.");}
        });
        timer.start(); statusLabel.setText("Executando...");
    }

    private void pausarExecucao(String message) { pararTimer(); statusLabel.setText(message); }
    private void pararTimer(){if(timer!=null)timer.stop();}

    private void reiniciarExecucao() {
        pararTimer(); cu.resetAll(); cycles=0; startTime=0;
        historyArea.setText("Estado reiniciado.\\n");
        statusLabel.setText("Reiniciado — memória limpa; monte o programa novamente.");
        atualizarPainel();
    }
    
    private String formatBinary(int[] bits) {

        StringBuilder binary = new StringBuilder();

        for (int bit : bits) {
            binary.append(bit);
        }

        return binary.toString();
    }
    private void atualizarPainel() {
        registerModel.setRowCount(0);
        String[] names={"PC","AC","SP","IR","TIR","ZERO","ONE","MINUS_ONE","AMASK","SMASK","A","B","C","D","E","F"};
        for(int i=0;i<names.length;i++){
            int[] bits = cu.getRegisters().getRegister(i).getRegister();
            
            int value=Conversion.binaryToDecimal(bits);
            registerModel.addRow(new Object[]{names[i],value,formatBinary(bits), String.format("0x%04X",value&0xFFFF)});
        }
        int pc=Conversion.binaryToDecimal(cu.getRegisters().getRegister(RegistersCollection.PC).getRegister());
        cyclesLabel.setText("Ciclos: "+cycles);
        timeLabel.setText("Tempo: "+(startTime==0?0:System.currentTimeMillis()-startTime)+" ms");
        atualizarMemoria();
        atualizarCache();
    }

    private void atualizarMemoria() {
        memoryModel.setRowCount(0);
        for(int address=0;address<4096;address++){
            int[] bits=cu.getMemory().read_Memory_storage(address);
            int value=Conversion.binaryToDecimal(bits);
            StringBuilder binary=new StringBuilder();
            for(int bit:bits) binary.append(bit);
            memoryModel.addRow(new Object[]{address,binary,value,String.format("%04X",value&0xFFFF)});
        }
    }
    
    
    private void atualizarCache() {

        CacheMemory cache = cu.getCache();

        cacheModel.setRowCount(0);

        for (int i = 0; i < cache.getSize(); i++) {

            boolean valid = cache.isValidAt(i);

            String address = valid
                ? String.valueOf(cache.getAddressAt(i))
                : "-";

            String data = valid
                ? formatBinary(cache.getDataAt(i))
                : "-";

            cacheModel.addRow(new Object[]{
                i,
                valid ? "Sim" : "Não",
                address,
                data
            });
        }

        cacheHitsLabel.setText("Hits: " + cache.getHits());

        cacheMissesLabel.setText("Misses: " + cache.getMisses());

        cacheRateLabel.setText(
            String.format("Hit Rate: %.2f%%", cache.getHitRate())
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new WindowRun().setVisible(true));
    }
}

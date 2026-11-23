# Simulador MIC-1

## Executar

Requer JDK 8 ou superior. Na pasta `Mic1`, compile com Maven (`mvn package`) e execute o JAR gerado em `target/`.

## Assembly

- Uma instrução por linha.
- Labels no formato `nome:` podem aparecer sozinhas ou antes de uma instrução na mesma linha.
- `JUMP`, `JPOS`, `JZER`, `JNEG`, `JNZE` e `CALL` aceitam labels ou endereços numéricos absolutos.
- Comentários podem começar com `//` ou `;`.
- `HALT` não recebe operando e encerra a execução assim que a instrução é carregada no registrador IR.
- Endereços numéricos podem ser decimais ou hexadecimais (`0x...`).

Exemplo:

```asm
LOCO 5
STOD 100
LOCO 0
ADDD 100
JZER fim
LOCO 1
fim: HALT
```

O montador faz duas passagens: primeiro registra os endereços dos labels e depois resolve os operandos e grava o código na memória.

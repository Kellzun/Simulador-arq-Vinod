package Viadedados;

public class ULA{
    private static ULA ula = null;
    private int[] A, B, F, D, R;
    public int[] getA(){return A;}
    public int[] getB(){return B;}
    public int[] getF(){return F;}
    private ULA(){
        R = new int[16];
        D = new int[2]; //D para verificar se é Negativo ou Zero
    }
    public static ULA getULA(){
        if(ula == null){
            ula = new ULA();
        }
        return ula;
    }
    public void executa(){
        //Somar
        if(F[0] == 0 && F[1]==0){
            int carry = 0;
            for(int i = 15; i>=0; i--){
                int result = A[i] + B[i] + carry;
                if(result == 0){
                    carry = 0;
                    R[i] = 1;
                }
                else if(result == 1){
                    carry = 0;
                    R[i] = 1;
                }
                else if(result == 2){
                    carry = 1;
                    R[i] = 0;
                }
                else if(result == 3){
                    carry = 1;
                    R[i] = 1;
                }
            }
        }
        //Operação lógica AND
        else if(F[0] == 0 && F[1] == 1){
            for(int i = 0; i<16; i++){
                if(A[i] == 1 && B[i] == 1){
                    R[i] = 1;
                }
                else{
                    R[i] = 0;
                }
            }
        }
        //Identidade
        else if(F[0] == 1 && F[1] == 0){
            System.arraycopy(A,0,R,0,16);
        }

        else if(F[0] == 1 && F[1] == 1){
            for(int i = 0; i<16; i++){
                if(A[i] == 0){
                    R[i] = 1;
                }
                else{
                    R[i] = 0;
                }
            }
        }

        if(R[0] == 1){
            D[0] = 1;
        }
        else{
            D[0] = 0;
        }
        //Zero flag
        boolean zeroninguer = true;
        for(int i = 0; i<16; i++){
            if(R[i] != 0){
                zeroninguer = false;
                break;
            }
        }
        if(zeroninguer){
            D[1] = 1;
        }
        else{
            D[1] = 0;
        }
    }
    public void setA(int[] x){
        A = x;
    }
    public void setB(int[] x){
        B = x;
    }
    public void setF(int[] x){
        F = x;
    }
    public int[] getD(){
        return D;
    }
    public int[] getR(){
        return R;
    }
}
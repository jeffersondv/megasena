/*
 * roleta.java
 *
 * Created on 8 de Agosto de 2010, 04:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package megasena2;

import java.util.Date;

/**
 *
 * @author Jefferson
 */
public class roleta {
    
    /** Creates a new instance of roleta */
    public roleta() {        
        vetor = new int[60];
        escolha = new int[6];
        tempoInicial = new Date();
    }
    
    public void sorteia(){
        
        int x;
        
        //repaint();
        /*
        System.out.print(jProgressBar1.getPercentComplete()+"    ");
        System.out.println(jProgressBar1.getValue());        
        System.out.println(" tempo: "+(new Date().getTime() - tempoInicial.getTime())/1000.0);
        if ((new Date().getTime() - tempoInicial.getTime())/100.0 % 1 == 0)
            jProgressBar1.setValue(jProgressBar1.getValue()+1);
        */
        //jProgressBar1.setValue(50);
        
        for (x=0; x <60; x++){
            vetor[x] = x+1;
        }
        for(x=0; x < 1000; x++){
            int temp,pos1, pos2, pos3, pos4;
            pos1 = Math.round((int)Math.random()*(vetor.length-1));
            pos2 = Math.round((int)Math.random()*(vetor.length-1));
            pos3 = Math.round((int)Math.random()*(vetor.length-1));
            pos4 = Math.round((int)Math.random()*(vetor.length-1));
            
            if (pos1 != pos2){
                temp = vetor[pos1];
                vetor[pos1] = vetor[pos2];
                vetor[pos2] = temp;
            }
            if (pos3 != pos4){
                temp = vetor[pos3];
                vetor[pos3] = vetor[pos4];
                vetor[pos4] = temp;
            }
        }
        int valor,pos,y;
        for (x=0; x <1000; x++){
            valor = vetor[(int)(Math.random()*x)%60];
            pos = (int)(Math.random()*x)%6;
            for (y=0; y <6; y++){
                if (escolha[y] == valor){
                    valor = 0;
                }
            }
            if (valor != 0)
                escolha[pos] = valor;
        }
        
    }
    
    public int[] getResultado(){        
        return escolha;        
    }
    
    private int[] escolha;
    private int[] vetor;
    private Date tempoInicial;
    
    
}

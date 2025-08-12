/*
 * arquivos.java
 *
 * Created on 11 de Agosto de 2010, 02:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package megasena2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jefferson
 */
public class arquivos {
    
    /** Creates a new instance of arquivos */
    public arquivos(String filename) {
    
        rawfile = new File(filename);
        try {
        if (!rawfile.exists()){
            rawfile.createNewFile();
        }
        //fw = new FileWriter(rawfile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }        
        System.out.print("pode escrever: "+rawfile.canWrite());
    }
    
    public String LeArquivo(){
        try {
            if (rawfile.exists()){
                fr = new FileReader(rawfile);
                BufferedReader fbuffer = new BufferedReader(fr, 1000);
                chars = new String();
                //String tempchar = new String();                  
                int n, numchar;
                char ch[] = new char[2];
                n = 1;                
                numchar = fbuffer.read(ch);
                while (numchar > 1){
                    
                    if (Character.isDigit(ch[0])){
                        chars = chars.concat(String.valueOf(Character.toChars(ch[0])));
                        
                    }else{
                        if (ch[0] == ' '){
                            chars = chars.concat(" ");                            
                        }else{                            
                            chars = chars.concat(";");
                        }
                    }
                    if (Character.isDigit(ch[1])){
                        chars = chars.concat(String.valueOf(Character.toChars(ch[1])));
                    }else{
                        if (ch[1] == ' '){
                            chars = chars.concat(" ");                            
                        }else{                            
                            chars = chars.concat(";");
                        }
                        
                    }
                        //chars = chars.concat(String.valueOf(Character.toChars(ch[0])));
                        //chars = chars.concat(String.valueOf(Character.toChars(ch[1])));
                    
                        //chars = chars.concat(String.valueOf(ch[0]+ch[1]));
//                        if (n % 6 == 0){
//                            chars = chars.concat(";");
//                            n = 1;
//                        }
                    //}
                    numchar = fbuffer.read(ch); //tempchar = tempchar.valueOf(Character.toChars());
                }
                fr.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return chars;
    }
    
    
    public String LeArquivo2(){
        try {
            if (rawfile.exists()){
                fr = new FileReader(rawfile);
                BufferedReader fbuffer = new BufferedReader(fr, 10000);
                chars = new String();
                //String tempchar = new String();                  
                int n, numchar;
                String strlinha;// = new String();
                n = 1;
                strlinha = fbuffer.readLine();
                while (fbuffer.ready()){
                    chars = chars.concat(strlinha+"\n");
                    /*for (String temp : strlinha.split(" ")){
                    
                        //if (Character.isDigit(temp.)){
                            chars = chars.concat(temp);
                        //}//chars = chars.concat(String.valueOf(Character.toChars(ch[0])));
                    }*/
                    
                    /*}else{
                        if (ch[0] == ' '){
                            chars = chars.concat(" ");                            
                        }else{                            
                            chars = chars.concat(";");
                        }
                    }
                    if (Character.isDigit(ch[1])){
                        chars = chars.concat(String.valueOf(Character.toChars(ch[1])));
                    }else{
                        if (ch[1] == ' '){
                            chars = chars.concat(" ");                            
                        }else{                            
                            chars = chars.concat(";");
                        }
                        
                    }
                        //chars = chars.concat(String.valueOf(Character.toChars(ch[0])));
                        //chars = chars.concat(String.valueOf(Character.toChars(ch[1])));
                    
                        //chars = chars.concat(String.valueOf(ch[0]+ch[1]));
//                        if (n % 6 == 0){
//                            chars = chars.concat(";");
//                            n = 1;
//                        }
                    //}
                    numchar = fbuffer.read(ch); //tempchar = tempchar.valueOf(Character.toChars());
                     */
                    strlinha = fbuffer.readLine();
                    //wait(5);
                }
                fr.close();
            }
        } catch (IOException ex) {
            //ex.printStackTrace();
        /*} catch (InterruptedException ex) {
            Logger.getLogger(arquivos.class.getName()).log(Level.SEVERE, null, ex);
            */
            Logger.getLogger(arquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chars;
    }
    
    
    public void gravaArquivo(String dados){
        try {                        
            if (rawfile.exists()){
                //fw.append(dados);
                fw = new FileWriter(rawfile, true);
                BufferedWriter fwbuffer = new BufferedWriter(fw);
                fwbuffer.append(dados);
                fwbuffer.close();
                //System.out.print(dados);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
        
    private File rawfile;
    private FileWriter fw;
    private FileReader fr;
    private String chars;
}

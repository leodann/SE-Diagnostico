package com.company.indice;

import com.company.motroInferncia.Reglas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class archivoMaestro {
    int llave,nodo1,nodo2,peso;
    int ren,col,celda;
    arbolIndice ari = new arbolIndice();

    public void escribir_archivo_maestro(ArrayList<Reglas> reglas) throws IOException {

        RandomAccessFile archi = new RandomAccessFile("conocimiento", "rw");
        int llave;
        long lreg = 0;
        long posicion;

        if (archi.length()>0){
            lreg = tamReg();
            archi.seek(archi.length() - tamReg());
            llave = archi.readInt();
            archi.seek(archi.length());
            for (int i = 0; i < reglas.size(); i++) {
                Reglas regla1 = reglas.get(i);
                archi.writeInt(llave);
                for (int j = 0; j < 8; j++) {
                    if (j < regla1.getAntecedentes().length){
                        archi.writeChars(regla1.getAntecedentes()[j]);
                    }else
                    {
                        String cvacia = "                              ";
                        archi.writeChars(cvacia);
                    }

                }
                archi.writeChars(regla1.getConsecuente());
                posicion = (llave-1)*lreg;
                ari.Insertar(llave,posicion);
                llave++;
            }//for

        }else {
            llave = 1;
            for (int i = 0; i < reglas.size(); i++) {
                Reglas regla1 = reglas.get(i);
                archi.writeInt(llave);
                for (int j = 0; j < 8; j++) {
                    if (j < regla1.getAntecedentes().length){
                        archi.writeChars(regla1.getAntecedentes()[j]);
                    }else
                    {
                        String cvacia = "                              ";
                        archi.writeChars(cvacia);
                    }

                }
                archi.writeChars(regla1.getConsecuente());
                posicion = (llave-1)*lreg;
                System.out.println("llave y pos: " + llave +","+ posicion);
                ari.Insertar(llave,posicion);
                lreg = tamReg();
                llave++;
            }
        }
        archi.close();
    }

    public void escribirAct() throws IOException {
        RandomAccessFile escribir = new RandomAccessFile("grafo","rw");
        long pfinal = escribir.length();

        escribir.seek(pfinal);
        escribir.writeInt(100);
        escribir.writeInt(45);
        escribir.close();

    }

    public void recuperarSecuencial() throws FileNotFoundException, IOException
    {
        long ap_actual,ap_final;
        String consec = "";
        Reglas regla;
        RandomAccessFile leer_archi = new RandomAccessFile("conocimiento","r");
        ArrayList<String> antecedentes = new ArrayList<>();
        ArrayList<Reglas> reglasLeer = new ArrayList<Reglas>();

        while((ap_actual=leer_archi.getFilePointer())!=(ap_final=leer_archi.length()))
        {
            System.out.println("puntero "+leer_archi.getFilePointer());
            System.out.println("tamaño "+leer_archi.length());
            llave=leer_archi.readInt();
            System.out.println("Llave: " + llave);
            for (int i = 0; i < 8; i++) {
                String ant = "";
                for (int j = 0; j < 30; j++) {
                    ant += leer_archi.readChar();
                }
                System.out.println("ant num " + i + ": " + ant);
                antecedentes.add(ant);
            }
            consec = "";
            for (int i = 0; i < 30; i++) {
                consec += leer_archi.readChar();
            }
            System.out.println("Consecuente: " + consec);
            regla = new Reglas(antecedentes,consec,llave);
            reglasLeer.add(regla);
        }

    }

    public void recuperarAleatorio()throws IOException{
        int n,llav;
        long dl;
        String consec = "";
        RandomAccessFile leer_archi = new RandomAccessFile("conocimiento","r");
        Scanner entrada = new Scanner(System.in);
        do{
            System.out.println("\nIntroduce la llave del registro: ");
            llav = entrada.nextInt();
            dl=ari.Buscar(llav);
            System.out.println("llav y dl " + llav+","+ dl);
            leer_archi.seek(dl);
            llave=leer_archi.readInt();
            System.out.println("\nLos datos del registro son: ");
            System.out.println("Llave: " + llave);
            for (int i = 0; i < 8; i++) {
                String ant = "";
                for (int j = 0; j < 30; j++) {
                    ant += leer_archi.readChar();
                }
                System.out.println("ant num " + i + ": " + ant);
            }
            consec = "";
            for (int i = 0; i < 30; i++) {
                consec += leer_archi.readChar();
            }
            System.out.println("Consecuente: " + consec);
            System.out.println("¿Quieres hacer otra busqueda aleatoria? si=1 no=0");
            n = entrada.nextInt();
        }while (n==1);
        leer_archi.close();
    }

    public long tamReg() throws IOException {
        RandomAccessFile archi = new RandomAccessFile("conocimiento", "rw");
        archi.readInt();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 30; j++) {
                archi.readChar();
            }
        }
        return archi.getFilePointer();
    }
}

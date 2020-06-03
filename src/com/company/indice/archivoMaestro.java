package com.company.indice;

import com.company.motroInferncia.Reglas;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.xml.bind.SchemaOutputResolver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class archivoMaestro {
    int llave,nodo1,nodo2,peso;
    int ren,col,celda;
    arbolIndice ari = new arbolIndice();

    public archivoMaestro (){}

    public boolean inicializar(){
        boolean existeArchivo = false;
        try {
            long fin;
            RandomAccessFile archi = new RandomAccessFile("conocimiento","r");
            System.out.println("ＥＬ   ＡＲＣＨＩＶＯ   ＥＸＩＳＴＥ");
            existeArchivo = true;
            fin = archi.length();
            archi.close();

            ArrayList<Reglas> conocimiento = recuperarSecuencial("conocimiento",0,fin);
            long lreg = tamReg();
            int llave = 0;
            long pos = 0;
            for (Reglas regla : conocimiento){
                llave = regla.getIndice();
                pos = (llave-1)*lreg;
                ari.Insertar(llave,pos);
            }
        } catch (Exception e) {
            System.out.println("ＥＬ   ＡＲＣＨＩＶＯ  ＮＯ ＥＸＩＳＴＥ");
            System.out.println("ＩＮＧＲＥＳＥ  ＲＥＧＬＡＳ  ＥＮ   ＬＡ  ＢＡＳＥ  ＤＥ  ＣＯＮＯＣＩＭＩＥＮＴＯＳ");
            existeArchivo = false;
        }

        return existeArchivo;
    }

    public void escribir_archivo(ArrayList<Reglas> reglas , String archiNombre) throws IOException {
        RandomAccessFile archi = new RandomAccessFile(archiNombre, "rw");
        if  (archi.length()!=0){
            System.out.println("ＳＯＢＲＥＳＣＲＩＢＩＥＮＤＯ   ＥＬ   ＡＲＣＨＩＶＯ");
        }
        int llave;
        long lreg = 0;
        long posicion;
        ari = new arbolIndice();
        archi.setLength(0);
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
            ari.Insertar(llave,posicion);
            lreg = tamReg();
            llave++;
        }
        archi.close();
    }

    public void añadir (ArrayList<Reglas> reglas) throws IOException {
        RandomAccessFile archi = new RandomAccessFile("conocimiento", "rw");
        int llave;
        long lreg = 0;
        long posicion;
        if (archi.length()!=0){
            System.out.println("ＡＧＲＥＧＡＮＤＯ   ＲＥＧＬＡＳ");
            lreg = tamReg();
            archi.seek(archi.length() - tamReg());
            llave = archi.readInt()+1;
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
            }
        }else{
            System.out.println("ＡＲＣＨＩＶＯ   ＶＡＣＩＯ");
        }

    }

    public void escribirAct() throws IOException {
        RandomAccessFile escribir = new RandomAccessFile("grafo","rw");
        long pfinal = escribir.length();

        escribir.seek(pfinal);
        escribir.writeInt(100);
        escribir.writeInt(45);
        escribir.close();

    }

    public ArrayList<Reglas> recuperarSecuencial(String archiNombre, long posIni, long posFin) throws FileNotFoundException, IOException
    {
        String consec = "";
        Reglas regla;
        RandomAccessFile leer_archi = new RandomAccessFile(archiNombre,"r");
        ArrayList<String> antecedentes;
        ArrayList<Reglas> reglasLeer = new ArrayList<Reglas>();
        leer_archi.seek(posIni);
        //while((ap_actual=leer_archi.getFilePointer())!=(ap_final=leer_archi.length()))
        while(posIni!=posFin)
        {
            antecedentes = new ArrayList<>();
            llave=leer_archi.readInt();
            for (int i = 0; i < 8; i++) {
                String ant = "";
                for (int j = 0; j < 30; j++) {
                    ant += leer_archi.readChar();
                }
                antecedentes.add(ant);
            }
            consec = "";
            for (int i = 0; i < 30; i++) {
                consec += leer_archi.readChar();
            }
            regla = new Reglas(antecedentes,consec,llave);
            reglasLeer.add(regla);
            posIni = leer_archi.getFilePointer();
        }
        return reglasLeer;
    }

    public Reglas buscarAleatorio(int llave) throws IOException {
        boolean existe = existeLlave(llave);
        RandomAccessFile archi = new RandomAccessFile("conocimiento","rw");
        long pos = ari.Buscar(llave);
        int rllave;
        String consec = "";
        ArrayList<String>rantecedentes = new ArrayList<>();
        Reglas reglabuscada = new Reglas();

        if(!existe){
            System.out.println("ＬＡ  ＬＬＡＶＥ  ＩＮＧＲＥＳＡＤＡ  ＮＯ  ＥＸＩＳＴＥ");
        }else{
            if (pos<archi.length()){
                archi.seek(pos);
                rllave = archi.readInt();
                for (int i = 0; i < 8; i++) {
                    String ant = "";
                    for (int j = 0; j < 30; j++) {
                        ant += archi.readChar();
                    }
                    rantecedentes.add(ant);
                }
                consec = "";
                for (int i = 0; i < 30; i++) {
                    consec += archi.readChar();
                }
                reglabuscada = new Reglas(rantecedentes,consec,llave);

            }else{
                System.out.println(" ＬＡ   ＬＬＡＶＥ   ＩＮＧＲＥＳＡＤＡ   ＮＯ   ＳＥ ＥＮＣＵＥＮＴＲＡ   ＥＮ   ＬＡ   ＢＡＳＥ   ＤＥ   ＣＯＮＯＣＩＭＩＥＮＴＯＳ");
            }
        }
        archi.close();
        return reglabuscada;
    }

    public void recuperarAleatorio()throws IOException{
        Scanner entrada = new Scanner(System.in);
        int llave, n;
        Reglas reglaObtenida;
        do{
            System.out.println("\nIntroduce la llave del registro: ");
            llave = entrada.nextInt();
            System.out.println("\nLos datos del registro son: ");
            reglaObtenida = buscarAleatorio(llave);
            reglaObtenida.mostrarRegla();
            System.out.println("¿Quieres hacer otra busqueda aleatoria? si=1 no=0");
            n = entrada.nextInt();
        }while (n==1);
    }

    public void eliminar (int llave) throws IOException {
        boolean existe = existeLlave(llave);
        if (!existe){
            System.out.println("ＬＡ  ＬＬＡＶＥ  ＩＮＧＲＥＳＡＤＡ  ＮＯ  ＥＸＩＳＴＥ");
        }else{
            long pos = ari.Buscar(llave);
            long fin = getLength("conocimiento");
            ArrayList<Reglas> reglasLeer1 = recuperarSecuencial("conocimiento",0,pos);
            pos += tamReg();
            ArrayList<Reglas> reglasLee2 = recuperarSecuencial("conocimiento",pos,fin);
            for (Reglas r2 : reglasLee2){
                reglasLeer1.add(r2);
            }
            escribir_archivo(reglasLeer1,"conocimiento");
            //ArrayList<Reglas>eliminada = recuperarSecuencial("conocimiento2",0,getLength("conocimiento2"));
        }
    }

    public void editar(int llave,List<String>nuevosAntecedentes, String nuevoConsecuente) throws IOException {
        boolean existe = existeLlave(llave);
        if (!existe){
            System.out.println("ＬＡ  ＬＬＡＶＥ  ＩＮＧＲＥＳＡＤＡ  ＮＯ  ＥＸＩＳＴＥ");
        }else{
            long pos = ari.Buscar(llave);
            long fin = getLength("conocimiento");
            Reglas reglaAct = new Reglas();
            ArrayList<String>ant = new ArrayList<>();
            ArrayList<Reglas>reglasLeer1 = recuperarSecuencial("conocimiento",0,pos);
            for (int i = 0; i <8 ; i++) {
                if (i<nuevosAntecedentes.size()){
                    ant.add(nuevosAntecedentes.get(i));
                }else{
                    String cvacia = "                              ";
                    ant.add(cvacia);
                }
            }
            pos += tamReg();
            ArrayList<Reglas>reglasLeer2 = recuperarSecuencial("conocimiento",pos,fin);
            reglaAct = new Reglas(ant,nuevoConsecuente,0);
            reglasLeer1.add(reglaAct);
            for (Reglas r2 : reglasLeer2){
                reglasLeer1.add(r2);
            }
            escribir_archivo(reglasLeer1,"conocimiento");
            //ArrayList<Reglas>updated = recuperarSecuencial("conocimiento2",0,getLength("conocimiento2"));
        }
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

    public long getLength(String archiNombre) throws IOException {
        RandomAccessFile archi = new RandomAccessFile(archiNombre,"r");
        return archi.length();
    }

    public ArrayList<Reglas>getBC () throws IOException {
        long fin = getLength("conocimiento");
        return recuperarSecuencial("conocimiento",0,fin);
    }

    public boolean existeLlave(int llave){
        boolean flag = false;
        long pl = ari.Buscar(llave);
        if (llave == 1){
            if (pl == 0){
                flag = true;
            }
        }else if(llave>1){
            if (pl!=0){
                flag = true;
            }else{
                flag = false;
            }
        }else{
            flag = false;
        }
        return flag;
    }
}

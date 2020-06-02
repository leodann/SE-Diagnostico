package com.company.indice;

import com.company.motroInferncia.Reglas;

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

    public archivoMaestro (){
        inicializar();
    }

    private void inicializar(){
        try {
            RandomAccessFile archi = new RandomAccessFile("conocimiento","r");
            System.out.println("el archivo existe");
            ArrayList<Reglas> conocimiento = recuperarSecuencial();
            long lreg = tamReg();
            int llave = 0;
            long pos = 0;
            for (Reglas regla : conocimiento){
                llave = regla.getIndice();
                pos = (llave-1)*lreg;
                ari.Insertar(llave,pos);
            }
        } catch (Exception e) {
            System.out.println("el archivo no existe");
            System.out.println("Ingrese raglas en la base de conocimiento");
        }
    }

    public void escribir_archivo_maestro(ArrayList<Reglas> reglas) throws IOException {

        RandomAccessFile archi = new RandomAccessFile("conocimiento", "rw");
        int llave;
        long lreg = 0;
        long posicion;

        if (archi.length()>0){
            System.out.println("Añadiendo reglas");
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
            System.out.println("Crenado base de conocimientos");
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

    public ArrayList<Reglas> recuperarSecuencial() throws FileNotFoundException, IOException
    {
        long ap_actual,ap_final;
        String consec = "";
        Reglas regla;
        RandomAccessFile leer_archi = new RandomAccessFile("conocimiento","r");
        ArrayList<String> antecedentes;
        ArrayList<Reglas> reglasLeer = new ArrayList<Reglas>();

        while((ap_actual=leer_archi.getFilePointer())!=(ap_final=leer_archi.length()))
        {
            antecedentes = new ArrayList<>();
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
        System.out.println("ANTECENDENTES RECORRER SECUENCIAL");
        for (Reglas r : reglasLeer){
            r.mostrarAntecedentes();
            System.out.println("-->"+r.getConsecuente());
            System.out.println("");
        }
        return reglasLeer;
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

    ///PRUEBAS

    public ArrayList<Reglas> recuperarSecuencial2() throws FileNotFoundException, IOException
    {
        long ap_actual,ap_final;
        String consec = "";
        Reglas regla;
        RandomAccessFile leer_archi = new RandomAccessFile("conocimiento2","rw");
        ArrayList<String> antecedentes;
        ArrayList<Reglas> reglasLeer = new ArrayList<Reglas>();

        while((ap_actual=leer_archi.getFilePointer())!=(ap_final=leer_archi.length()))
        {
            antecedentes = new ArrayList<>();
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
        return reglasLeer;
    }

    public void eliminar (int llave) throws IOException {
        long pos = ari.Buscar(llave);
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("POSICION A ELIMINAR : "+ pos);
        RandomAccessFile leer_archi = new RandomAccessFile("conocimiento","rw");
        //raf.seek(pos);
        //busqueda secuencial hasta pos -> arr1
        long ap_actual,ap_final;
        String consec = "";
        Reglas regla;

        ArrayList<String> antecedentes;
        ArrayList<Reglas> reglasLeer1 = new ArrayList<Reglas>();

        while((ap_actual=leer_archi.getFilePointer())!=(ap_final=pos))
        {
            antecedentes = new ArrayList<>();
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
            reglasLeer1.add(regla);
        }
        pos += tamReg();
        leer_archi.seek(pos);
        if (pos == leer_archi.length()){
            System.out.println("final del archivo");
        }else{
            while((ap_actual=pos)!=(ap_final=leer_archi.length()))
            {
                antecedentes = new ArrayList<>();
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
                reglasLeer1.add(regla);
                pos = leer_archi.getFilePointer();
            }

        }
        leer_archi.close();
        rescribir(reglasLeer1);
    }

    public void editar (int llave,List<String>nuevosAntecedentes,String nuevoConsecuente) throws IOException {
        long pos = ari.Buscar(llave);
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("POSICION A EDITAR : "+ pos);
        RandomAccessFile leer_archi = new RandomAccessFile("conocimiento","rw");
        //raf.seek(pos);
        //busqueda secuencial hasta pos -> arr1
        long ap_actual,ap_final;
        String consec = "";
        Reglas regla;
        
        ArrayList<String> antecedentes;
        ArrayList<Reglas> reglasLeer1 = new ArrayList<Reglas>();        

        while((ap_actual=leer_archi.getFilePointer())!=(ap_final=pos))
        {
            antecedentes = new ArrayList<>();
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
            reglasLeer1.add(regla);
        }
        for (int l = 0; l<9;l++){
            if (l>nuevosAntecedentes.size()) {
                String cvacia = "                              ";
                nuevosAntecedentes.add(cvacia);
            }
        }
        reglasLeer1.add(new Reglas(nuevosAntecedentes,nuevoConsecuente,0));
        System.out.println(" ¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡ "+pos);
        pos += tamReg();
        System.out.println(" ¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡¡ "+pos);
        leer_archi.seek(pos);
        if (pos == leer_archi.length()){
            System.out.println("final del archivo");
        }else{

            while((ap_actual=pos)!=(ap_final=leer_archi.length()))
            {
                antecedentes = new ArrayList<>();
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
                reglasLeer1.add(regla);
                pos = leer_archi.getFilePointer();
            }

        }
        leer_archi.close();
        rescribir(reglasLeer1);

    }

    public void rescribir(ArrayList<Reglas> reglas) throws IOException {
        System.out.println("}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
        for (Reglas r : reglas){
            r.mostrarAntecedentes();
            System.out.println("-->"+r.getConsecuente());
            System.out.println("");
        }
        System.out.println("T A M A Ñ O   R E G L A S "+reglas.size());

        RandomAccessFile aux = new RandomAccessFile("conocimiento2","rw");
        aux.setLength(0);
        System.out.println("PRIMER INTETO");
        //CONOCIMIENTO EDITADO
        int llave;
        long lreg = 0;
        long posicion;

        ari = new arbolIndice();
        System.out.println("Crenado base de conocimientos");
        llave = 1;
        System.out.println("T A M A Ñ O       A N T E S    D E     F O R : " +aux.length());
        for (int i = 0; i < reglas.size(); i++) {
            Reglas regla1 = reglas.get(i);
            aux.writeInt(llave);
            for (int j = 0; j < 8; j++) {
                if (j < regla1.getAntecedentes().length){
                    aux.writeChars(regla1.getAntecedentes()[j]);
                }else
                {
                    String cvacia = "                              ";
                    aux.writeChars(cvacia);
                }

            }
            aux.writeChars(regla1.getConsecuente());
            posicion = (llave-1)*lreg;
            System.out.println("llave y pos: " + llave +","+ posicion);
            ari.Insertar(llave,posicion);
            lreg = tamReg();
            llave++;
        }
        System.out.println("T A M A Ñ O       T O T A L :" +aux.length());
        aux.close();
        ArrayList<Reglas> con2 = recuperarSecuencial2();
    }
}

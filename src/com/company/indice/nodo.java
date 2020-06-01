package com.company.indice;

public class nodo {
    int llave;
    long dirl;
    nodo izq, der;

    public nodo(int ele,long dir, nodo I, nodo D){
        llave = ele;
        dirl = dir;
        izq = I;
        der = D;
    }

    public nodo(int ele, long dir){
        this(ele,dir,null,null);
    }
}

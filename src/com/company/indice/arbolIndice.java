package com.company.indice;

public class arbolIndice {
    nodo raiz = null;

    public void Insertar(int llave,long p){
        nodo nuevo;
        nodo ant = null;
        nodo rec;
        if (raiz==null)
        {
            raiz = new nodo(llave,p);
            //System.out.println("valor insertado" + llave);
        }else{
            nuevo = new nodo(llave,p);
            //System.out.println("valpr insertado" + llave);
            rec = raiz;
            while (rec!=null){
                ant = rec;
                if (rec.llave > nuevo.llave)
                    rec = rec.izq;
                else
                    rec = rec.der;
            }
            if(ant.llave > nuevo.llave)
                ant.izq = nuevo;
            else
                ant.der = nuevo;
        }
    }//insertar

    public long Buscar(int llave){
        long p = 0;
        nodo rec;
        nodo ant;
        if(raiz == null)
            System.out.println("En este momento el arbol se encuentra vacio");
        else
        {
            rec = raiz;
            ant = raiz;
            while ((ant.llave!=llave)&&(rec!=null))
            {
                ant = rec;
                if (rec.llave>llave)
                    rec = rec.izq;
                else
                    rec = rec.der;
            }
            if(ant.llave==llave)
            {
                System.out.println("La llave se encuentra en el arbol: "+ ant.llave);
                p = ant.dirl;
            }
            else
                System.out.println("La llave " + llave + " no se encuentra en el arbol");
        }//else

        return p;
    }//buscar
}

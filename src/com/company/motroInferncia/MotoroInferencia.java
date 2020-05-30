package com.company.motroInferncia;

import java.util.ArrayList;


public class MotoroInferencia {
    private ArrayList<Reglas> conocimientos;
    private ArrayList<String> bh;
    private ArrayList<Integer> indice_Reglas;

    private ArrayList<Reglas>conjuntoConflicto;

    public MotoroInferencia(ArrayList<Reglas> Conocimientos, ArrayList<String> Hechos) {
        this.conocimientos = Conocimientos;
        this.bh = Hechos;
        this.indice_Reglas = new ArrayList<>();
        this.conjuntoConflicto = new ArrayList<>();
    }

    private void equiparar(ArrayList<Reglas>reglas,ArrayList<String>bh){
        boolean regla_equiparada = false;
        boolean antecedente_equipardo;
        //Recorremos las reglas
        for(Reglas regla : reglas){
            int i = 0;
            String [] antecedentes = regla.getAntecedentes();
            //Recorremos los antecedentes
            do{
                String antecedente = antecedentes[i];
                antecedente_equipardo = buscarEnBh(antecedente,bh);
                i++;
                //Se detiene cuando un antecedente no se encuentra en la BH o ya no hay más antecedentes
            }while(i<antecedentes.length && antecedente_equipardo);
            if  (antecedente_equipardo){
                regla_equiparada = true;
                //Generamos conjunto Conflicto
                conjuntoConflicto.add(regla);
                indice_Reglas.add(regla.getIndice());

            }else{
                regla_equiparada = false;
            }
        }
    }

    private boolean buscarEnBh(String elemento,ArrayList<String>bh){
        int j = 0;
        boolean existe;
        //Recorremos la BH
        do {
            String hecho = bh.get(j);
            //Comparamos que el antecedente exista en la base de hechos
            if (elemento.equalsIgnoreCase(hecho)){
                existe = true;
            }else{
                existe = false;
            }
            j++;
            //Se detiene cuando un antecedente se encuentra en la BH o ya no haya más hechos con que comparar
        }while(j<bh.size()&& !existe);
        return existe;
    }

    private void resolucion(String goal){
        boolean existe;
        int k = 0;
        String consecuente;
        //Recorremos conjunto conflicto
        do{
            consecuente = conjuntoConflicto.get(k).getConsecuente();
            //Comprobamos si el consecuente ya existe en la BH
            existe = buscarEnBh(consecuente,bh);
            //Si no existe el consecuente
            if (!existe){
                //Añadimos el cosencuente a la base hechos
                bh.add(consecuente);
                conjuntoConflicto.remove(k);
            }
            //Sigue recorreindo el ciclo meintras existan reglas en el conjunto conflicto
            //y el consecuente exista en la BH
        }while(existe && k<conjuntoConflicto.size());
        //Si no existe el consecuente en la BH sale del ciclo
        //Comprobar que el consecuente sea la meta
        if (consecuente.equalsIgnoreCase(goal)){
            System.out.println("El consecuente es la meta");
        }
        //Si el consecuente no es la meta, entonces solo es un nuevo hecho
        else{
            //Como la base de hechos cambio se tiene que buscar nuevas reglas que se puedan aplicar
            //Pero descartamos las reglas que ya teniamos en el conjunto conflicto
            ArrayList<Reglas> resto_reglas = restoConocimiento(indice_Reglas);
            equiparar(resto_reglas,bh);//Generamos nuevas reglas en el conjunto conflicto
            //Resolvemos
            resolucion(goal);
        }
    }

    public ArrayList<Reglas>restoConocimiento(ArrayList<Integer>indices){
        ArrayList<Reglas> conjunto_Reglas =new ArrayList<>();
        for (Integer indice : indices){
            for (Reglas resto : conocimientos){
                if (indice != resto.getIndice()){
                    conjunto_Reglas.add(resto);
                }
            }
        }
        return conjunto_Reglas;
    }
}

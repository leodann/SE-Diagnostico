package com.company.motroInferncia;

import java.util.ArrayList;


public class MotoroInferencia {
    private ArrayList<Reglas> conocimientos;
    private ArrayList<String> bh;
    private ArrayList<Integer> indice_Reglas;
    private ArrayList<Reglas>conjuntoConflicto;
    private ArrayList<Reglas>reglasAplicadas;
    private ArrayList<Reglas>reglasNoAplicables;

    public MotoroInferencia(ArrayList<Reglas> Conocimientos, ArrayList<String> Hechos) {
        this.conocimientos = Conocimientos;
        this.bh = Hechos;
        this.indice_Reglas = new ArrayList<>();
        this.conjuntoConflicto = new ArrayList<>();
        this.reglasAplicadas = new ArrayList<>();
        //inferir();
    }

    public ArrayList<String> inferir (String goal){
        equiparar(conocimientos,bh);
        conocimientos = reglasNoAplicables;
        resolucion(goal);
        return bh;
    }

    private void equiparar(ArrayList<Reglas>reglas,ArrayList<String>bh){
        ArrayList<Reglas>reglasNoEquiparadas = new ArrayList<>();
        boolean regla_equiparada = false;
        boolean antecedente_equipardo;
        //Recorremos las reglas
        for(Reglas regla : reglas){
            int i = 0;
            String [] antecedentes = regla.getAntecedentes();
            //Recorremos los antecedentes
            do{
                String antecedente = antecedentes[i];
                //Se busca que el antecedente este en la BH
                antecedente_equipardo = buscarEnBh(antecedente,bh);
                i++;
                //Si el antecedente no se encuentra en la BH o ya no hay más antecedentes se detiene el ciclo
            }while(i<antecedentes.length && antecedente_equipardo);
            //Si todos los antecedentes existen
            if  (antecedente_equipardo) {
                //La regla puede aplicarse
                regla_equiparada = true;
                //Generamos conjunto Conflicto
                conjuntoConflicto.add(regla);
                indice_Reglas.add(regla.getIndice());
            }else{
                regla_equiparada = false;
                reglasNoEquiparadas.add(regla);

            }
        }        
        reglasNoAplicables = reglasNoEquiparadas;

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

    //PROBLEMAS
    private void resolucion(String goal){
        boolean existe = false;
        if(conjuntoConflicto.isEmpty()){
        }else {
            String nuevoHecho = conjuntoConflicto.get(0).getConsecuente();
            existe = buscarEnBh(nuevoHecho, bh);

            if (!existe) {
                bh.add(nuevoHecho);
                equiparar(conocimientos, bh);
                conocimientos = reglasNoAplicables;
            }
            reglasAplicadas.add(conjuntoConflicto.get(0));
            conjuntoConflicto.remove(0);
            indice_Reglas.remove(0);

            if (!conjuntoConflicto.isEmpty() && !goal.equalsIgnoreCase(nuevoHecho)) {
                resolucion(goal);
            } else if (goal.equalsIgnoreCase(nuevoHecho)) {
            } else {
            }
        }
    }

    public ArrayList<Reglas> getReglasAplicadas() {
        return reglasAplicadas;
    }

    public void setReglasAplicadas(ArrayList<Reglas> reglasAplicadas) {
        this.reglasAplicadas = reglasAplicadas;
    }
}

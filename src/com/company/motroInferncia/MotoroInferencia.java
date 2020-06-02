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
        inferir();
    }

    private void inferir (){
        System.out.println("INICIANDO INFERENCIA");
        equiparar(conocimientos,bh);
        conocimientos = reglasNoAplicables;
        resolucion("A");
        System.out.println("Base de Hechos");
        mostrarBH();
        System.out.println("Conjunto Conflicto");
        mostrarConflicto();
        imprimirIndices();
        mostrarConocimiento();
    }

    private void equiparar(ArrayList<Reglas>reglas,ArrayList<String>bh){
        System.out.println("fase de Equiparacion");
        ArrayList<Reglas>reglasNoEquiparadas = new ArrayList<>();
        boolean regla_equiparada = false;
        boolean antecedente_equipardo;
        //Recorremos las reglas
        for(Reglas regla : reglas){
            System.out.println("Equiparando Regla");
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
            if  (antecedente_equipardo){
                //La regla puede aplicarse
                regla_equiparada = true;
                System.out.println("Añadiendo regla al conjunto conflicto");
                //Generamos conjunto Conflicto
                conjuntoConflicto.add(regla);
                indice_Reglas.add(regla.getIndice());

            }else{
                System.out.println("Regla no aplicable");
                regla_equiparada = false;
                reglasNoEquiparadas.add(regla);

            }
        }
        System.out.println("REGLAS NO EQUIPARABLES");
        for(Reglas r : reglasNoEquiparadas){
            System.out.println("");
            r.mostrarAntecedentes();
            System.out.print("-->"+r.getConsecuente());
            System.out.println("");
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
            System.out.println("La meta no se logro y ya no hay más reglas que aplicar");
        }else {
            String nuevoHecho = conjuntoConflicto.get(0).getConsecuente();
            existe = buscarEnBh(nuevoHecho, bh);

            if (!existe) {
                bh.add(nuevoHecho);
                equiparar(conocimientos, bh);
                conocimientos = reglasNoAplicables;
            }
            conjuntoConflicto.remove(0);
            indice_Reglas.remove(0);

            if (!conjuntoConflicto.isEmpty() && !goal.equalsIgnoreCase(nuevoHecho)) {
                resolucion(goal);
            } else if (goal.equalsIgnoreCase(nuevoHecho)) {
                System.out.println("La meta ha sido alcanzada");
            } else {
                System.out.println("La meta no se logro y ya no hay más reglas que aplicar");
            }
        }
    }

    private void imprimirIndices(){
        for(Integer indice : indice_Reglas){
            System.out.println("regla: "+ indice);
        }
    }

    private void mostrarBH(){
        for (int i = 0; i<bh.size();i++){
            System.out.println(bh.get(i));
        }
    }

    private void mostrarConflicto(){
        for (Reglas conflicto : conjuntoConflicto){
            conflicto.mostrarAntecedentes();
            System.out.print("-->"+conflicto.getConsecuente());
            System.out.println("");
        }
    }

    private void mostrarConocimiento(){
        System.out.println("REGLAS EN LA BASE DE CONOCIMIENTOS");
        for (Reglas conocimineto: conocimientos){
            conocimineto.mostrarAntecedentes();
            System.out.println("-->"+conocimineto.getConsecuente());
            System.out.println("");
        }
    }
}

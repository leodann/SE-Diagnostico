package com.company.motroInferncia;

import java.util.List;

public class Reglas {
    private List<String> list_antecedentes;
    private String [] antecedentes;
    private String consecuente;
    private int indice;

    public Reglas(List<String>list_antecedentes, String consecuente, int indice){
        this.list_antecedentes = list_antecedentes;
        convertirLista();
        this.consecuente = consecuente;
        this.indice = indice;
    }

    private void convertirLista(){
        int size = list_antecedentes.size();
        antecedentes = new String[size];
        for(int i = 0; i<size; i++){
            antecedentes[i] = list_antecedentes.get(i);
        }
    }

    public int getIndice(){
        return this.indice;
    }

    public void setIndice(int indice){
        this.indice = indice;
    }

    public List<String> getList_antecedentes() {
        return list_antecedentes;
    }

    public void setList_antecedentes(List<String> list_antecedentes) {
        this.list_antecedentes = list_antecedentes;
    }

    public String[] getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String[] antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getConsecuente() {
        return consecuente;
    }

    public void setConsecuente(String consecuente) {
        this.consecuente = consecuente;
    }
}

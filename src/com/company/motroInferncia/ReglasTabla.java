package com.company.motroInferncia;

public class ReglasTabla {
    private int llave;
    private String antecedentes;
    private String consecunte;

    public ReglasTabla(){}

    public ReglasTabla(int llave, String antecedentes, String consecunte) {
        this.llave = llave;
        this.antecedentes = antecedentes;
        this.consecunte = consecunte;
    }

    public int getLlave() {
        return llave;
    }

    public void setLlave(int llave) {
        this.llave = llave;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getConsecunte() {
        return consecunte;
    }

    public void setConsecunte(String consecunte) {
        this.consecunte = consecunte;
    }
}

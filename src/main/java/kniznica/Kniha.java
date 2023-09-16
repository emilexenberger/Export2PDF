package kniznica;

/*
Vytvorte knižnicu (Kniznica), kde bude môcť používateľ zadať knihy (názov, autor, rok vydania) cez konzolu kým nezadá slovo koniec. Tie na konci vypíšeme. Opakovanie na ArrayList.
 */

import java.io.Serial;
import java.io.Serializable;

public class Kniha implements Serializable {
    private String nazov;
    private String autor;
    private int rokVydania;
    private Double cena;

    @Serial
    private static final long serialVersionUID = -5196853542948616452L;

    public Kniha() {
    }

    public Kniha(String nazov, String autor, int rokVydania, Double cena) {
        this.nazov = nazov;
        this.autor = autor;
        this.rokVydania = rokVydania;
        this.cena = cena;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getRokVydania() {
        return rokVydania;
    }

    public void setRokVydania(int rokVydania) {
        this.rokVydania = rokVydania;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Kniha: " +
                "nazov = " + nazov +
                ", autor = " + autor +
                ", rok vydania = " + rokVydania +
                ", cena = " + cena ;
    }

    public String toStringWithIndex(int index) {
        return "Kniha c. " + index + ": " +
                "nazov = " + nazov +
                ", autor = " + autor +
                ", rok vydania = " + rokVydania +
                ", cena = " + cena;
    }
}

package ro.scholarship.model;

import jakarta.persistence.*;

@Entity
@Table(name = "criterii_sociale")
public class CriteriuSocial extends Criteriu {

    @Column(name = "venit_maxim")
    private float venitMaximAcceptat;

    public CriteriuSocial() {
        setTipCriteriu("SOCIAL");
    }

    public CriteriuSocial(int id, String denumire, float pondere, float venitMaximAcceptat) {
        super(id, denumire, pondere);
        this.venitMaximAcceptat = venitMaximAcceptat;
        setTipCriteriu("SOCIAL");
    }

    public float getVenitMaximAcceptat() {
        return venitMaximAcceptat;
    }

    public void setVenitMaximAcceptat(float venitMaximAcceptat) {
        this.venitMaximAcceptat = venitMaximAcceptat;
    }

    @Override
    public float evalueaza(Student student) {
        // Exemplu: dacă Student are getVenitFamilie() - altfel lasă 0.
        // return student.getVenitFamilie() <= venitMaximAcceptat ? getPondere() : 0;
        return 0;
    }

    @Override
    public String toString() {
        return "CriteriuSocial{" +
                "venitMaximAcceptat=" + venitMaximAcceptat +
                ", " + super.toString() +
                '}';
    }
}

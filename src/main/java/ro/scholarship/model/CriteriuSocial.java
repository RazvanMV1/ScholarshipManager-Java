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
        Double venitStudent = student.getVenitLunar();
        if (venitStudent == null) return 0f;
        // Dacă venitul studentului e sub maximul acceptat, primește punctaj (exemplu: invers proporțional cu venitul)
        return (venitStudent <= venitMaximAcceptat)
                ? getPondere() * (float) (venitMaximAcceptat - venitStudent)
                : 0f;
    }

    @Override
    public String toString() {
        return "CriteriuSocial{" +
                "venitMaximAcceptat=" + venitMaximAcceptat +
                ", " + super.toString() +
                '}';
    }
}

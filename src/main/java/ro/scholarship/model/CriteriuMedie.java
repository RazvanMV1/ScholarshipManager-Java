package ro.scholarship.model;

import jakarta.persistence.*;

@Entity
@Table(name = "criterii_medie")
public class CriteriuMedie extends Criteriu {

    @Column(name = "medie_minima")
    private float medieMinimaAcceptata;

    public CriteriuMedie() {
        setTipCriteriu("MEDIE");
    }

    public CriteriuMedie(int id, String denumire, float pondere, float medieMinimaAcceptata) {
        super(id, denumire, pondere);
        this.medieMinimaAcceptata = medieMinimaAcceptata;
        setTipCriteriu("MEDIE");
    }

    public float getMedieMinimaAcceptata() {
        return medieMinimaAcceptata;
    }

    public void setMedieMinimaAcceptata(float medieMinimaAcceptata) {
        this.medieMinimaAcceptata = medieMinimaAcceptata;
    }

    @Override
    public float evalueaza(Student student) {
        if (student.getMedieSemestruAnterior() < medieMinimaAcceptata) {
            return 0;
        }
        return student.getMedieSemestruAnterior() * getPondere();
    }

    @Override
    public String toString() {
        return "CriteriuMedie{" +
                "medieMinimaAcceptata=" + medieMinimaAcceptata +
                ", " + super.toString() +
                '}';
    }
}

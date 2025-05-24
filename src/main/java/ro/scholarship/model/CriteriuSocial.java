package ro.scholarship.model;

public class CriteriuSocial extends Criteriu {
    private float venitMaximAcceptat;

    public CriteriuSocial() {}

    public CriteriuSocial(int id, String denumire, float pondere, float venitMaximAcceptat) {
        super(id, denumire, pondere);
        this.venitMaximAcceptat = venitMaximAcceptat;
    }

    public float getVenitMaximAcceptat() {
        return venitMaximAcceptat;
    }

    public void setVenitMaximAcceptat(float venitMaximAcceptat) {
        this.venitMaximAcceptat = venitMaximAcceptat;
    }

    @Override
    public float evalueaza(Student student) {
        //Pentru exemplu, presupunem ca metoda getVenitFamilie() exista și returneaza venitul lunar pe membru.
        // Daca nu exista, returnezi 0.
        // return student.getVenitFamilie() <= venitMaximAcceptat ? getPondere() : 0;
        return 0; // Placeholder
    }

    @Override
    public String toString() {
        return "CriteriuSocial{" +
                "venitMaximAcceptat=" + venitMaximAcceptat +
                ", " + super.toString() +
                '}';
    }
}


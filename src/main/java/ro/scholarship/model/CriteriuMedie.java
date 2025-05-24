package ro.scholarship.model;

public class CriteriuMedie extends Criteriu {
    private float medieMinimaAcceptata;

    public CriteriuMedie() {}

    public CriteriuMedie(int id, String denumire, float pondere, float medieMinimaAcceptata) {
        super(id, denumire, pondere);
        this.medieMinimaAcceptata = medieMinimaAcceptata;
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

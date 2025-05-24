package ro.scholarship.model;

public abstract class Criteriu {
    private int id;
    private String denumire;
    private float pondere;

    public Criteriu() {}

    public Criteriu(int id, String denumire, float pondere) {
        this.id = id;
        this.denumire = denumire;
        this.pondere = pondere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public float getPondere() {
        return pondere;
    }

    public void setPondere(float pondere) {
        this.pondere = pondere;
    }

    public abstract float evalueaza(Student student);

    @Override
    public String toString() {
        return "Criteriu{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", pondere=" + pondere +
                '}';
    }
}

package ro.scholarship.model;

import jakarta.persistence.*;

@Entity
@Table(name = "criterii")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Criteriu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "criterii_seq_gen")
    @SequenceGenerator(name = "criterii_seq_gen", sequenceName = "criterii_seq", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String denumire;

    @Column(nullable = false)
    private float pondere;

    @Column(name = "tip_criteriu", nullable = false)
    private String tipCriteriu;

    @ManyToOne
    @JoinColumn(name = "bursa_id")
    private Bursa bursa;

    public Criteriu() {}

    public Criteriu(int id, String denumire, float pondere) {
        this.id = id;
        this.denumire = denumire;
        this.pondere = pondere;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDenumire() { return denumire; }
    public void setDenumire(String denumire) { this.denumire = denumire; }

    public float getPondere() { return pondere; }
    public void setPondere(float pondere) { this.pondere = pondere; }

    public String getTipCriteriu() { return tipCriteriu; }
    public void setTipCriteriu(String tipCriteriu) { this.tipCriteriu = tipCriteriu; }

    public Bursa getBursa() { return bursa; }
    public void setBursa(Bursa bursa) { this.bursa = bursa; }

    public abstract float evalueaza(Student student);

    @Override
    public String toString() {
        return "Criteriu{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", pondere=" + pondere +
                ", tipCriteriu='" + tipCriteriu + '\'' +
                '}';
    }
}
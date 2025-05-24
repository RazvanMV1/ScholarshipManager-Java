package ro.scholarship.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "semestre_universitare")
public class SemestruUniversitar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "semestre_universitare_seq_gen")
    @SequenceGenerator(name = "semestre_universitare_seq_gen", sequenceName = "semestre_universitare_seq", allocationSize = 1)
    private int id;

    @Column(name = "an_universitar", nullable = false)
    private int anUniversitar;

    @Column(nullable = false)
    private int semestru; // 1 sau 2

    @Column(name = "data_inceput")
    private LocalDate dataInceput;

    @Column(name = "data_sfarsit")
    private LocalDate dataSfarsit;

    public SemestruUniversitar() {}

    public SemestruUniversitar(int id, int anUniversitar, int semestru, LocalDate dataInceput, LocalDate dataSfarsit) {
        this.id = id;
        this.anUniversitar = anUniversitar;
        this.semestru = semestru;
        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAnUniversitar() { return anUniversitar; }
    public void setAnUniversitar(int anUniversitar) { this.anUniversitar = anUniversitar; }

    public int getSemestru() { return semestru; }
    public void setSemestru(int semestru) { this.semestru = semestru; }

    public LocalDate getDataInceput() { return dataInceput; }
    public void setDataInceput(LocalDate dataInceput) { this.dataInceput = dataInceput; }

    public LocalDate getDataSfarsit() { return dataSfarsit; }
    public void setDataSfarsit(LocalDate dataSfarsit) { this.dataSfarsit = dataSfarsit; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SemestruUniversitar)) return false;
        SemestruUniversitar that = (SemestruUniversitar) o;
        return id == that.id &&
                anUniversitar == that.anUniversitar &&
                semestru == that.semestru;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, anUniversitar, semestru);
    }

    @Override
    public String toString() {
        return "SemestruUniversitar{" +
                "id=" + id +
                ", anUniversitar=" + anUniversitar +
                ", semestru=" + semestru +
                ", dataInceput=" + dataInceput +
                ", dataSfarsit=" + dataSfarsit +
                '}';
    }
}

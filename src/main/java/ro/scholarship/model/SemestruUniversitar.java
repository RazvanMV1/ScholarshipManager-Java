package ro.scholarship.model;

import java.time.LocalDate;
import java.util.Objects;

public class SemestruUniversitar {
    private int id;
    private int anUniversitar;
    private int semestru; // 1 sau 2
    private LocalDate dataInceput;
    private LocalDate dataSfarsit;

    public SemestruUniversitar() {}

    public SemestruUniversitar(int id, int anUniversitar, int semestru, LocalDate dataInceput, LocalDate dataSfarsit) {
        this.id = id;
        this.anUniversitar = anUniversitar;
        this.semestru = semestru;
        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnUniversitar() {
        return anUniversitar;
    }

    public void setAnUniversitar(int anUniversitar) {
        this.anUniversitar = anUniversitar;
    }

    public int getSemestru() {
        return semestru;
    }

    public void setSemestru(int semestru) {
        this.semestru = semestru;
    }

    public LocalDate getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(LocalDate dataInceput) {
        this.dataInceput = dataInceput;
    }

    public LocalDate getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(LocalDate dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

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

package ro.scholarship.model;

public enum TipBursa {
    MERIT("Bursa de merit"),
    SOCIALA("Bursa sociala");

    private final String descriere;

    TipBursa(String descriere) {
        this.descriere = descriere;
    }

    public String getDescriere() {
        return descriere;
    }

    @Override
    public String toString() {
        return descriere;
    }
}


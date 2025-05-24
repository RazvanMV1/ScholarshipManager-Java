package ro.scholarship.model;

public enum TipBursa {
    MERIT("Bursa de merit"),
    STUDIU("Bursa de studiu"),
    SOCIALA("Bursa sociala"),
    PERFORMANTA("Bursa de performanta"),
    SPECIALA("Bursa speciala");

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


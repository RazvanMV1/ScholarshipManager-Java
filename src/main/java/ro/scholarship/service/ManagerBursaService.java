package ro.scholarship.service;

import ro.scholarship.model.BursaAcordata;
import java.util.List;

public interface ManagerBursaService {
    List<BursaAcordata> proceseazaBursa(int idBursa);
}

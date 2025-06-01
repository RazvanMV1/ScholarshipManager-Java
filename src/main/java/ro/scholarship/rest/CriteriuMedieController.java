package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.CriteriuMedie;
import ro.scholarship.repository.CriteriuMedieRepository;

import java.util.List;

@RestController
@RequestMapping("/api/criterii-medie")
public class CriteriuMedieController {

    @Autowired
    private CriteriuMedieRepository criteriuMedieRepository;

    @GetMapping
    public List<CriteriuMedie> getAll() {
        return criteriuMedieRepository.findAll();
    }

    @GetMapping("/{id}")
    public CriteriuMedie getById(@PathVariable int id) {
        return criteriuMedieRepository.findById(id).orElse(null);
    }

    @PostMapping
    public CriteriuMedie add(@RequestBody CriteriuMedie criteriuMedie) {
        return criteriuMedieRepository.save(criteriuMedie);
    }

    @PutMapping("/{id}")
    public CriteriuMedie update(@PathVariable int id, @RequestBody CriteriuMedie criteriuMedie) {
        criteriuMedie.setId(id);
        return criteriuMedieRepository.save(criteriuMedie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        criteriuMedieRepository.deleteById(id);
    }

    // Extra: criterii medie pentru o bursă
    @GetMapping("/bursa/{bursaId}")
    public List<CriteriuMedie> getByBursa(@PathVariable int bursaId) {
        var bursa = new ro.scholarship.model.Bursa();
        bursa.setId(bursaId);
        return criteriuMedieRepository.findByBursa(bursa);
    }
}

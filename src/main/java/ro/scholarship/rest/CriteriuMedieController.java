package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.CriteriuMedie;
import ro.scholarship.service.CriteriuMedieService;

import java.util.List;

@RestController
@RequestMapping("/api/criterii-medie")
public class CriteriuMedieController {

    @Autowired
    private CriteriuMedieService criteriuMedieService;

    @GetMapping
    public List<CriteriuMedie> getAll() {
        return criteriuMedieService.findAll();
    }

    @GetMapping("/{id}")
    public CriteriuMedie getById(@PathVariable int id) {
        return criteriuMedieService.findById(id).orElse(null);
    }

    @PostMapping
    public CriteriuMedie add(@RequestBody CriteriuMedie criteriuMedie) {
        return criteriuMedieService.save(criteriuMedie);
    }

    @PutMapping("/{id}")
    public CriteriuMedie update(@PathVariable int id, @RequestBody CriteriuMedie criteriuMedie) {
        return criteriuMedieService.update(id, criteriuMedie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        criteriuMedieService.delete(id);
    }

    @GetMapping("/bursa/{bursaId}")
    public List<CriteriuMedie> getByBursa(@PathVariable int bursaId) {
        return criteriuMedieService.findByBursa(bursaId);
    }
}

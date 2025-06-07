package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.Criteriu;
import ro.scholarship.model.Bursa;
import ro.scholarship.repository.CriteriuRepository;

import java.util.List;

@RestController
@RequestMapping("/api/criterii")
public class CriteriuController {

    @Autowired
    private CriteriuRepository criteriuRepository;

    @GetMapping
    public List<Criteriu> getAll() {
        return criteriuRepository.findAll();
    }

    @GetMapping("/{id}")
    public Criteriu getById(@PathVariable int id) {
        return criteriuRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Criteriu add(@RequestBody Criteriu criteriu) {
        return criteriuRepository.save(criteriu);
    }

    @PutMapping("/{id}")
    public Criteriu update(@PathVariable int id, @RequestBody Criteriu criteriu) {
        criteriu.setId(id);
        return criteriuRepository.save(criteriu);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        criteriuRepository.deleteById(id);
    }

    @GetMapping("/bursa/{bursaId}")
    public List<Criteriu> getByBursa(@PathVariable int bursaId) {
        Bursa bursa = new Bursa();
        bursa.setId(bursaId);
        return criteriuRepository.findByBursa(bursa);
    }
}

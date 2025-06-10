package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.Criteriu;
import ro.scholarship.service.CriteriuService;

import java.util.List;

@RestController
@RequestMapping("/api/criterii")
public class CriteriuController {

    @Autowired
    private CriteriuService criteriuService;

    @GetMapping
    public List<Criteriu> getAll() {
        return criteriuService.findAll();
    }

    @GetMapping("/{id}")
    public Criteriu getById(@PathVariable int id) {
        return criteriuService.findById(id).orElse(null);
    }

    @PostMapping
    public Criteriu add(@RequestBody Criteriu criteriu) {
        return criteriuService.save(criteriu);
    }

    @PutMapping("/{id}")
    public Criteriu update(@PathVariable int id, @RequestBody Criteriu criteriu) {
        return criteriuService.update(id, criteriu);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        criteriuService.delete(id);
    }

    @GetMapping("/bursa/{bursaId}")
    public List<Criteriu> getByBursa(@PathVariable int bursaId) {
        return criteriuService.findByBursa(bursaId);
    }
}

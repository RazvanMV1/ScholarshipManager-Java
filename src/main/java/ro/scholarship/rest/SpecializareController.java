package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.Specializare;
import ro.scholarship.repository.SpecializareRepository;

import java.util.List;

@RestController
@RequestMapping("/api/specializari")
public class SpecializareController {

    @Autowired
    private SpecializareRepository specializareRepository;

    @GetMapping
    public List<Specializare> getAll() {
        return specializareRepository.findAll();
    }

    @GetMapping("/{id}")
    public Specializare getById(@PathVariable int id) {
        return specializareRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Specializare add(@RequestBody Specializare s) {
        return specializareRepository.save(s);
    }

    @PutMapping("/{id}")
    public Specializare update(@PathVariable int id, @RequestBody Specializare s) {
        s.setId(id);
        return specializareRepository.save(s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        specializareRepository.deleteById(id);
    }
}

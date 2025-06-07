package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.SemestruUniversitar;
import ro.scholarship.repository.SemestruUniversitarRepository;

import java.util.List;

@RestController
@RequestMapping("/api/semestre")
public class SemestruUniversitarController {

    @Autowired
    private SemestruUniversitarRepository semestruRepo;

    @GetMapping
    public List<SemestruUniversitar> getAll() {
        return semestruRepo.findAll();
    }

    @GetMapping("/{id}")
    public SemestruUniversitar getById(@PathVariable int id) {
        return semestruRepo.findById(id).orElse(null);
    }

    @PostMapping
    public SemestruUniversitar add(@RequestBody SemestruUniversitar sem) {
        return semestruRepo.save(sem);
    }

    @PutMapping("/{id}")
    public SemestruUniversitar update(@PathVariable int id, @RequestBody SemestruUniversitar sem) {
        sem.setId(id);
        return semestruRepo.save(sem);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        semestruRepo.deleteById(id);
    }

    @GetMapping("/an/{anUniversitar}")
    public List<SemestruUniversitar> getByAnUniversitar(@PathVariable int anUniversitar) {
        return semestruRepo.findByAnUniversitar(anUniversitar);
    }
}

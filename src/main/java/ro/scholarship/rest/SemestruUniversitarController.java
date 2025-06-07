package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.SemestruUniversitar;
import ro.scholarship.service.SemestruUniversitarService;

import java.util.List;

@RestController
@RequestMapping("/api/semestre")
public class SemestruUniversitarController {

    @Autowired
    private SemestruUniversitarService semestruService;

    @GetMapping
    public List<SemestruUniversitar> getAll() {
        return semestruService.findAll();
    }

    @GetMapping("/{id}")
    public SemestruUniversitar getById(@PathVariable int id) {
        return semestruService.findById(id).orElse(null);
    }

    @PostMapping
    public SemestruUniversitar add(@RequestBody SemestruUniversitar sem) {
        return semestruService.save(sem);
    }

    @PutMapping("/{id}")
    public SemestruUniversitar update(@PathVariable int id, @RequestBody SemestruUniversitar sem) {
        return semestruService.update(id, sem);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        semestruService.delete(id);
    }

    @GetMapping("/an/{anUniversitar}")
    public List<SemestruUniversitar> getByAnUniversitar(@PathVariable int anUniversitar) {
        return semestruService.findByAnUniversitar(anUniversitar);
    }
}

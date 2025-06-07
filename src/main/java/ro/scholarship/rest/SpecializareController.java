package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.Specializare;
import ro.scholarship.service.SpecializareService;

import java.util.List;

@RestController
@RequestMapping("/api/specializari")
public class SpecializareController {

    @Autowired
    private SpecializareService specializareService;

    @GetMapping
    public List<Specializare> getAll() {
        return specializareService.findAll();
    }

    @GetMapping("/{id}")
    public Specializare getById(@PathVariable int id) {
        return specializareService.findById(id).orElse(null);
    }

    @PostMapping
    public Specializare add(@RequestBody Specializare s) {
        return specializareService.save(s);
    }

    @PutMapping("/{id}")
    public Specializare update(@PathVariable int id, @RequestBody Specializare s) {
        return specializareService.update(id, s);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        specializareService.delete(id);
    }
}

package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.Facultate;
import ro.scholarship.repository.FacultateRepository;

import java.util.List;

@RestController
@RequestMapping("/api/facultati")
public class FacultateController {

    @Autowired
    private FacultateRepository facultateRepository;

    @GetMapping
    public List<Facultate> getAll() {
        return facultateRepository.findAll();
    }
    @GetMapping("/ping")
    public String ping() {
        return "merge!";
    }
    @GetMapping("/debug")
    public List<Facultate> debug() {
        List<Facultate> list = facultateRepository.findAll();
        System.out.println("Facultati din DB: " + list.size());
        for (Facultate f : list) {
            System.out.println(f);
        }
        return list;
    }

    @GetMapping("/{id}")
    public Facultate getById(@PathVariable int id) {
        return facultateRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Facultate add(@RequestBody Facultate f) {
        return facultateRepository.save(f);
    }

    @PutMapping("/{id}")
    public Facultate update(@PathVariable int id, @RequestBody Facultate f) {
        f.setId(id);
        return facultateRepository.save(f);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        facultateRepository.deleteById(id);
    }
}

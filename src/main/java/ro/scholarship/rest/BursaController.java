package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.Bursa;
import ro.scholarship.repository.BursaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/burse")
public class BursaController {
    @Autowired
    private BursaRepository bursaRepository;

    @GetMapping
    public List<Bursa> getAll() {
        return bursaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Bursa getById(@PathVariable int id) {
        return bursaRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Bursa add(@RequestBody Bursa b) {
        return bursaRepository.save(b);
    }

    @PutMapping("/{id}")
    public Bursa update(@PathVariable int id, @RequestBody Bursa b) {
        b.setId(id);
        return bursaRepository.save(b);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bursaRepository.deleteById(id);
    }
}

package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.Bursa;
import ro.scholarship.service.BursaService;

import java.util.List;

@RestController
@RequestMapping("/api/burse")
public class BursaController {

    @Autowired
    private BursaService bursaService;

    @GetMapping
    public List<Bursa> getAll() {
        return bursaService.findAll();
    }

    @GetMapping("/{id}")
    public Bursa getById(@PathVariable int id) {
        return bursaService.findById(id).orElse(null);
    }

    @PostMapping
    public Bursa add(@RequestBody Bursa b) {
        return bursaService.save(b);
    }

    @PutMapping("/{id}")
    public Bursa update(@PathVariable int id, @RequestBody Bursa b) {
        return bursaService.update(id, b);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bursaService.delete(id);
    }
}

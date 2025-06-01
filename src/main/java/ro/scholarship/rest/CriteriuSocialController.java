package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.CriteriuSocial;
import ro.scholarship.repository.CriteriuSocialRepository;
import ro.scholarship.model.Bursa;

import java.util.List;

@RestController
@RequestMapping("/api/criterii-sociale")
public class CriteriuSocialController {

    @Autowired
    private CriteriuSocialRepository criteriuSocialRepository;

    @GetMapping
    public List<CriteriuSocial> getAll() {
        return criteriuSocialRepository.findAll();
    }

    @GetMapping("/{id}")
    public CriteriuSocial getById(@PathVariable int id) {
        return criteriuSocialRepository.findById(id).orElse(null);
    }

    @PostMapping
    public CriteriuSocial add(@RequestBody CriteriuSocial criteriuSocial) {
        return criteriuSocialRepository.save(criteriuSocial);
    }

    @PutMapping("/{id}")
    public CriteriuSocial update(@PathVariable int id, @RequestBody CriteriuSocial criteriuSocial) {
        criteriuSocial.setId(id);
        return criteriuSocialRepository.save(criteriuSocial);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        criteriuSocialRepository.deleteById(id);
    }

    // Criterii sociale pentru o bursă
    @GetMapping("/bursa/{bursaId}")
    public List<CriteriuSocial> getByBursa(@PathVariable int bursaId) {
        Bursa bursa = new Bursa();
        bursa.setId(bursaId);
        return criteriuSocialRepository.findByBursa(bursa);
    }
}

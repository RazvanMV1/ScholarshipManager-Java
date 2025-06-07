package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.CriteriuSocial;
import ro.scholarship.model.Bursa;
import ro.scholarship.service.CriteriuSocialService;

import java.util.List;

@RestController
@RequestMapping("/api/criterii-sociale")
public class CriteriuSocialController {

    @Autowired
    private CriteriuSocialService criteriuSocialService;

    @GetMapping
    public List<CriteriuSocial> getAll() {
        return criteriuSocialService.findAll();
    }

    @GetMapping("/{id}")
    public CriteriuSocial getById(@PathVariable int id) {
        return criteriuSocialService.findById(id).orElse(null);
    }

    @PostMapping
    public CriteriuSocial add(@RequestBody CriteriuSocial criteriuSocial) {
        return criteriuSocialService.save(criteriuSocial);
    }

    @PutMapping("/{id}")
    public CriteriuSocial update(@PathVariable int id, @RequestBody CriteriuSocial criteriuSocial) {
        return criteriuSocialService.update(id, criteriuSocial);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        criteriuSocialService.delete(id);
    }

    @GetMapping("/bursa/{bursaId}")
    public List<CriteriuSocial> getByBursa(@PathVariable int bursaId) {
        Bursa bursa = new Bursa();
        bursa.setId(bursaId);
        return criteriuSocialService.findByBursa(bursa);
    }
}

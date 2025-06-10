package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.BursaAcordata;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.Student;
import ro.scholarship.service.BursaAcordataService;

import java.util.List;

@RestController
@RequestMapping("/api/burse-acordate")
public class BursaAcordataController {

    @Autowired
    private BursaAcordataService bursaAcordataService;

    @GetMapping
    public List<BursaAcordata> getAll() {
        return bursaAcordataService.findAll();
    }

    @GetMapping("/{id}")
    public BursaAcordata getById(@PathVariable int id) {
        return bursaAcordataService.findById(id).orElse(null);
    }

    @PostMapping
    public BursaAcordata add(@RequestBody BursaAcordata ba) {
        return bursaAcordataService.save(ba);
    }

    @PutMapping("/{id}")
    public BursaAcordata update(@PathVariable int id, @RequestBody BursaAcordata ba) {
        return bursaAcordataService.update(id, ba);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bursaAcordataService.delete(id);
    }

    @GetMapping("/student/{studentId}")
    public List<BursaAcordata> getByStudent(@PathVariable int studentId) {
        Student student = new Student();
        student.setId(studentId);
        return bursaAcordataService.findByStudent(student);
    }

    @GetMapping("/bursa/{bursaId}")
    public List<BursaAcordata> getByBursa(@PathVariable int bursaId) {
        Bursa bursa = new Bursa();
        bursa.setId(bursaId);
        return bursaAcordataService.findByBursa(bursa);
    }
}

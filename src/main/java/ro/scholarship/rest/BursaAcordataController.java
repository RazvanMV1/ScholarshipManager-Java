package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.BursaAcordata;
import ro.scholarship.model.Bursa;
import ro.scholarship.model.Student;
import ro.scholarship.repository.BursaAcordataRepository;

import java.util.List;

@RestController
@RequestMapping("/api/burse-acordate")
public class BursaAcordataController {

    @Autowired
    private BursaAcordataRepository bursaAcordataRepository;

    @GetMapping
    public List<BursaAcordata> getAll() {
        return bursaAcordataRepository.findAll();
    }

    @GetMapping("/{id}")
    public BursaAcordata getById(@PathVariable int id) {
        return bursaAcordataRepository.findById(id).orElse(null);
    }

    @PostMapping
    public BursaAcordata add(@RequestBody BursaAcordata ba) {
        return bursaAcordataRepository.save(ba);
    }

    @PutMapping("/{id}")
    public BursaAcordata update(@PathVariable int id, @RequestBody BursaAcordata ba) {
        ba.setId(id);
        return bursaAcordataRepository.save(ba);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        bursaAcordataRepository.deleteById(id);
    }

    // Extra: GET burse acordate pentru un student
    @GetMapping("/student/{studentId}")
    public List<BursaAcordata> getByStudent(@PathVariable int studentId) {
        Student student = new Student();
        student.setId(studentId);
        return bursaAcordataRepository.findByStudent(student);
    }

    // Extra: GET burse acordate pentru o bursă
    @GetMapping("/bursa/{bursaId}")
    public List<BursaAcordata> getByBursa(@PathVariable int bursaId) {
        Bursa bursa = new Bursa();
        bursa.setId(bursaId);
        return bursaAcordataRepository.findByBursa(bursa);
    }
}

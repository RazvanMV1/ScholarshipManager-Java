package ro.scholarship.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.scholarship.model.BursaAcordata;
import ro.scholarship.service.ManagerBursaService;

import java.util.List;

@RestController
@RequestMapping("/api/manager-burse")
public class ManagerBursaController {

    @Autowired
    private ManagerBursaService managerBursaService;

    @PostMapping("/proceseaza/{idBursa}")
    public List<BursaAcordata> proceseazaBursa(@PathVariable int idBursa) {
        return managerBursaService.proceseazaBursa(idBursa);
    }
}

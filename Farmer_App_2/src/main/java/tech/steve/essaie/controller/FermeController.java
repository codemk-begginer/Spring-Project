package tech.steve.essaie.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.FermeDto;
import tech.steve.essaie.service.FermeService;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class FermeController {

    private FermeService fermeService;

    @PostMapping(path = "createFerme")
    public void inscription(@RequestBody FermeDto fermeDto){
        log.info("create");

        this.fermeService.create(fermeDto);

    }

    @GetMapping(  path = "read", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FermeDto> read(){
        log.info("read");
        return this.fermeService.findAll();
    }

    @PutMapping (path = "/update/{id}")
    public ResponseEntity<FermeDto> update(@PathVariable Long id, @RequestBody  FermeDto fermeDto){
        log.info("update");

        return ResponseEntity.ok(fermeService.update(id, fermeDto));

    }

    @PostMapping(path = "/delete/{id}")
    public void inscription(@PathVariable Long id){
        log.info("delete");

        this.fermeService.delete(id);

    }

    @GetMapping(path = "/findById/{id}")
    public ResponseEntity<FermeDto> findById(@PathVariable Long id){
        log.info("findById");
        return ResponseEntity.ok(fermeService.findById(id));
    }





//    @GetMapping(  path = "findByNom", produces = MediaType.APPLICATION_JSON_VALUE)
//    public  List<FermeDto> findBynom(@RequestParam(required = false) String nom ){
//        log.info("findByNom");
//        return this.fermeService.findByNom(nom);
//    }






}

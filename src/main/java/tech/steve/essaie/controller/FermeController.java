package tech.steve.essaie.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.steve.essaie.dto.ferme.FermeDto;
import tech.steve.essaie.dto.ferme.FermeUpdateDto;
import tech.steve.essaie.service.FermeService;

import java.util.List;
import java.util.UUID;

/**
 * <b>Crud endpoints for ferme class</b>
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("ferme")
public class FermeController {

    private FermeService fermeService;

    /**
     * create a ferme
     * @param fermeDto fermeDto
     */
    @PostMapping(path = "createFerme")
    public void inscription(@RequestBody FermeDto fermeDto){
        log.info("create");

        this.fermeService.create(fermeDto);

    }

    /**
     * @return List of all ferme
     */
    @GetMapping(  path = "read", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FermeDto> read(){
        log.info("read");
        return this.fermeService.findAll();
    }

    /**
     * use id property of ferme class and fermeDto to update an information in ferme
     * @param id fermeId
     * @param fermeDto fermeDto
     * @return updated ferme
     */

    @PutMapping (path = "/update/{id}")
    public ResponseEntity<FermeUpdateDto> update(@Parameter(description = "ID de la ferme a mettre a jour") @PathVariable UUID id, @RequestBody  FermeUpdateDto fermeDto){
        log.info("update");

        return ResponseEntity.ok(fermeService.update(id, fermeDto));

    }

    /**
     * use id property of ferme class to delete a ferme
     * @param id fermeId
     */
    @PostMapping(path = "/delete/{id}")
    public void inscription(@Parameter(description = "ID de la ferme a supprimee")@PathVariable UUID id){
        log.info("delete");

        this.fermeService.delete(id);

    }

    /**
     * use id property of ferme class to list a ferme
     * @param id fermeId
     * @return list of ferme
     */
    @GetMapping(path = "/findById/{id}")
    public ResponseEntity<FermeDto> findById(@Parameter(description = "ID de la ferme rechercher")@PathVariable UUID id){
        log.info("findById");
        return ResponseEntity.ok(fermeService.findById(id));
    }


//    @GetMapping(  path = "findByNom", produces = MediaType.APPLICATION_JSON_VALUE)
//    public  List<FermeDto> findBynom(@RequestParam(required = false) String nom ){
//        log.info("findByNom");
//        return this.fermeService.findByNom(nom);
//    }






}

package tech.chilo.imc;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "imc", consumes = APPLICATION_JSON_VALUE , produces = APPLICATION_JSON_VALUE)
public class ImcController {


    private final ImcService imcService;



    public ImcController(ImcService imcService) {
        this.imcService = imcService;
    }


    @PostMapping
    public @ResponseBody Map<String,  String> calculer(@RequestBody ImcDTO data){
        return imcService.calculate(data);
    }
}

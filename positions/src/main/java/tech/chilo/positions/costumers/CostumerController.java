package tech.chilo.positions.costumers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "costumer")
@AllArgsConstructor
public class CostumerController {

    private final CostumerService costumerService;

    @GetMapping
    public List<Costumer> search(){
        return this.costumerService.search();
    }
}

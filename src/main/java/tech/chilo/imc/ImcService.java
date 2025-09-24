package tech.chilo.imc;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImcService {
    public Map<String, String> calculate(ImcDTO data ){
        double imc = data.poids()/Math.pow(data.taille(), 2);
        imc = Math.round(imc * 100)/100;
        String advise = this.analyse(imc);
        return Map.of("imc",String.valueOf(imc),
                "avis",advise);

    }

    private String analyse (double imc){
        if (imc<16.5){
            return "élévé";
        } else if (imc>=16.5 && imc<=18.5) {
            return "Accrue";
        } else if (imc>=18.5 && imc<=25) {
            return "faible";
        } else if (imc>=25 && imc<=30) {
            return "pré-obésité";
        } else if (imc>=30 && imc <=35) {
            return "obésité modéré";
        }else if (imc>=35 && imc <=40) {
            return "obésité sévère";
        }else  {
            return "obésité morbide";
     }
    }
}

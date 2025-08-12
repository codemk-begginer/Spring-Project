package tech.chilo.avis.advice;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.Map;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = MalformedJwtException.class)
    public Map<String,String> exceptionsHandler(){

        return Map.of("faillure" ,"opération non autorisé");
    }

 @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = BadCredentialsException.class)
    public @ResponseBody
 ProblemDetail BadCredentialsException(BadCredentialsException exception){

        log.error(exception.getMessage(),exception);
     ProblemDetail problemDetail = ProblemDetail
             .forStatusAndDetail(UNAUTHORIZED,"identifiants invalides");
     problemDetail.setProperty("erreur ","nous n'avons pas pue vous identifier");
     return problemDetail;
    }

@ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(value = SignatureException.class)
    public @ResponseBody
 ProblemDetail SignatureException( SignatureException exception){
     log.error(exception.getMessage(),exception);
    ProblemDetail problemDetail =  ProblemDetail
             .forStatusAndDetail(UNAUTHORIZED,"votre token est invalide");
    problemDetail.setProperty("erreur ","verifier votre token");
    return problemDetail;

    }


}

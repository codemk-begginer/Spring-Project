package tech.steve.essaie.config;

import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Naissance;
import tech.steve.essaie.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfExportUtil {

    public byte[] generateTransactionsPdf(List<Transaction> data) {
        // TODO: Implémenter avec iText / OpenPDF / JasperReports
        return new byte[0];
    }

    public byte[] generateAnimauxPdf(List<Animal> data) {
        // TODO: Implémenter
        return new byte[0];
    }

    public byte[] generateNaissancesPdf(List<Naissance> data) {
        // TODO: Implémenter
        return new byte[0];
    }
}

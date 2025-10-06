package tech.steve.essaie.service.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openpdf.text.*;
import org.openpdf.text.Font;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import tech.steve.essaie.dto.animal.AnimalDto;
import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.dto.TransactionDto;
import tech.steve.essaie.dto.animal.AnimalExportDto;
import tech.steve.essaie.mapper.AnimalExportMapper;
import tech.steve.essaie.mapper.AnimalMapper;
import tech.steve.essaie.mapper.NaissanceMapper;
import tech.steve.essaie.mapper.TransactionMapper;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.Naissance;
import tech.steve.essaie.model.Transaction;
import tech.steve.essaie.repository.AnimalRepository;
import tech.steve.essaie.repository.NaissanceRepository;
import tech.steve.essaie.repository.TransactionRepository;
import tech.steve.essaie.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final TransactionRepository transactionRepository;
    private final AnimalRepository animalRepository;
    private final NaissanceRepository naissanceRepository;
    private final AnimalMapper animalMapper;
    private final AnimalExportMapper animalExportMapper;
    private final TransactionMapper transactionMapper;
    private final NaissanceMapper naissanceMapper;



//    public TransactionDto toDto(Transaction transaction) {
//        TransactionDto dto = new TransactionDto();
//        dto.setId(transaction.getId());
//        dto.setType(transaction.getType());
//        dto.setCategorie(transaction.getCategorie());
//        dto.setMontant(transaction.getMontant());
//        dto.setDate(transaction.getDate());
//        dto.setDescription(transaction.getDescription());
//        dto.setNomIntervenant(transaction.getNomIntervenant());
//        dto.setTelephoneIntervenant(transaction.getTelephoneIntervenant());
//
//        // récupérer seulement les IDs des relations
//        dto.setAnimalId(transaction.getAnimal() != null ? transaction.getAnimal().getId() : null);
//        dto.setFermeId(transaction.getFerme() != null ? transaction.getFerme().getId() : null);
//
//        return dto;
//    }


    @Override
    public void exportTransactionsToPdf(HttpServletResponse response) throws IOException {
        List<TransactionDto> data = transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDto)
                .toList();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Titre du document
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Liste des Transactions", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(new Paragraph(" "));

        // Créer un tableau PDF
        PdfPTable table = new PdfPTable(9); // 9 colonnes pour ID, Nom, Sexe
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Ajouter les en-têtes de colonnes
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(2);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHeader.setSize(9);
        fontHeader.setColor(new Color(0,0,0));


        cell.setPhrase(new Phrase("Type de" +
                " Transaction", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Categorie", fontHeader));
        table.addCell(cell);
         cell.setPhrase(new Phrase("Montant", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Description", fontHeader));
        table.addCell(cell);
         cell.setPhrase(new Phrase("Animal", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nom de " +
                "l'intervenant", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Telephone de " +
                "l'intervenant", fontHeader));
        table.addCell(cell);
           cell.setPhrase(new Phrase("Ferme", fontHeader));
        table.addCell(cell);



        // Ajouter les données de la base de données
        for (TransactionDto transaction : data) {
            table.addCell(transaction.type().toString());
            table.addCell(transaction.categorie().toString());
            table.addCell(transaction.montant().toString());
            table.addCell(transaction.date().toString());
            table.addCell(transaction.description());
            table.addCell(transaction.animalId().toString());
            table.addCell(transaction.nomIntervenant());
            table.addCell(transaction.telephoneIntervenant());
            table.addCell(transaction.fermeId().toString());


        }

        // Ajouter le tableau au document
        document.add(table);

        document.close();
    }

    @Override
    public void exportTransactionsToExcel(HttpServletResponse response) throws IOException {
        List<TransactionDto> data = transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toDto)
                .toList();
                final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Users");

        final Row header = sheet.createRow(0);

        header.createCell(1).setCellValue("typeTransaction");
        header.createCell(2).setCellValue("categorie");
        header.createCell(3).setCellValue("montant");
        header.createCell(4).setCellValue("date");
        header.createCell(5).setCellValue("description");
        header.createCell(6).setCellValue("animal");
        header.createCell(7).setCellValue("nomIntervennt");
        header.createCell(8).setCellValue("TelephoneIntervenant");
        header.createCell(9).setCellValue("ferme");

        int rowNum = 1 ;

        for(TransactionDto transaction: data){
            final Row row = sheet.createRow(rowNum++);

            row.createCell(1).setCellValue(transaction.type().ordinal());
            row.createCell(2).setCellValue(transaction.categorie().toString());
            row.createCell(3).setCellValue( transaction.montant().toString());
            row.createCell(4).setCellValue(transaction.date());
            row.createCell(5).setCellValue(transaction.description());
            row.createCell(6).setCellValue( transaction.animalId().toString());
            row.createCell(7).setCellValue(transaction.nomIntervenant());
            row.createCell(8).setCellValue(transaction.telephoneIntervenant());
            row.createCell(9).setCellValue( transaction.fermeId().toString());
        }

        final ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }



    @Override
    public void exportAnimauxToPdf(HttpServletResponse response) throws IOException {
       List<AnimalExportDto> data = animalRepository.findAll()
                .stream()
                .map(animalExportMapper::toDto)
                .toList();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Titre du document
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Liste des Animaux", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(new Paragraph(" "));

        // Créer un tableau PDF
        PdfPTable table = new PdfPTable(9); // 9 colonnes
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Ajouter les en-têtes de colonnes
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(2);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHeader.setSize(9);
        fontHeader.setColor(new Color(0,0,0));



        cell.setPhrase(new Phrase("Date de" +
                " Naissance", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date de " +
                "Mort", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nom", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Generation", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Pere", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Mere", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("status", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("sexe" , fontHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Observations" , fontHeader));
        table.addCell(cell);





        // Ajouter les données de la base de données
        for (AnimalExportDto animal : data) {

            table.addCell(animal.dateNaissance().toString());
            table.addCell(animal.dateMort().toString());
            table.addCell(animal.nom());
            table.addCell(animal.generation().toString());
            if (animal.truie() != null) {
                table.addCell(animal.truie().toString());
            }else {
                table.addCell("");
            }
             if (animal.verrat() != null) {
                table.addCell(animal.verrat().toString());
            }else {
                 table.addCell("");
             }

            table.addCell(animal.statut().toString());
            table.addCell(animal.sexe().toString());
            table.addCell(animal.observations());


        }

        // Ajouter le tableau au document
        document.add(table);

        document.close();


    }

    @Override
    public void exportAnimauxToExcel(HttpServletResponse response) throws IOException  {
        List<AnimalExportDto> data = animalRepository.findAll()
                .stream()
                .map(animalExportMapper::toDto)
                .toList();

            final Workbook workbook = new XSSFWorkbook();
            final Sheet sheet = workbook.createSheet("Users");

            final Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("nom");
            header.createCell(1).setCellValue("Generation");
            header.createCell(2).setCellValue("mere");
            header.createCell(3).setCellValue("pere");
            header.createCell(4).setCellValue("status");
            header.createCell(5).setCellValue("date de" +
                    "Naissance");
            header.createCell(6).setCellValue("date de" +
                    "Mort");
            header.createCell(7).setCellValue("Observations");
            header.createCell(8).setCellValue("sexe");

            int rowNum = 1 ;

            for(AnimalExportDto animal: data){
                final Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(animal.nom());
                row.createCell(1).setCellValue(animal.generation());
                if (animal.truie() != null) {
                    row.createCell(2).setCellValue(String.valueOf(animal.truie()));
                }
                if (animal.verrat() != null) {
                    row.createCell(3).setCellValue(String.valueOf(animal.verrat()));
                }


                row.createCell(4).setCellValue(animal.statut().toString());
                row.createCell(5).setCellValue( animal.dateNaissance().toString());
                row.createCell(6).setCellValue(animal.dateMort().toString());
                row.createCell(7).setCellValue(animal.observations());
                row.createCell(8).setCellValue(animal.sexe().toString());

            }

            final ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
    }



    @Override
    public void exportNaissancesToPdf(HttpServletResponse response) throws IOException {
        List<NaissanceDto> data = naissanceRepository.findAll()
                .stream()
                .map(naissanceMapper::toDto)
                .toList();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Titre du document
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Liste des Naissance", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.add(new Paragraph(" "));

        // Créer un tableau PDF
        PdfPTable table = new PdfPTable(9); // 9 colonnes
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Ajouter les en-têtes de colonnes
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(2);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHeader.setSize(9);
        fontHeader.setColor(new Color(0,0,0));


        cell.setPhrase(new Phrase("Numero", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("nom de" +
                " l'intervenant", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Ferme", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("telephone de " +
                "l'intervenant", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Observations", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date de " +
                "Naissance", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Croisement", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nombre de " +
                "vivant", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Nombre de " +
                "Mort" , fontHeader));
        table.addCell(cell);





        // Ajouter les données de la base de données
        for (NaissanceDto naissance : data) {
            table.addCell(naissance.id().toString());
            table.addCell(naissance.nomIntervenant());
            table.addCell(naissance.fermeId().toString());
            table.addCell(naissance.telephoneIntervenant());
            table.addCell(naissance.observations());
            table.addCell(naissance.dateNaissance().toString());
            table.addCell(naissance.croisementId().toString());
            table.addCell(naissance.nbVivant().toString());
            table.addCell(naissance.nbMort().toString());

        }

        // Ajouter le tableau au document
        document.add(table);

        document.close();

    }

    @Override
    public void exportNaissancesToExcel(HttpServletResponse response) throws IOException  {
        List<NaissanceDto> data = naissanceRepository.findAll()
                .stream()
                .map(naissanceMapper::toDto)
                .toList();
        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Users");

        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("nom de" +
                "l'intervenant");
        header.createCell(1).setCellValue("Ferme");
        header.createCell(2).setCellValue("Telephone de " +
                "l'intervenant");
        header.createCell(3).setCellValue("Observations");
        header.createCell(4).setCellValue("date de" +
                "Naissance");

        header.createCell(5).setCellValue("Croisement" );
        header.createCell(6).setCellValue("Nombre de " +
                "Vivant");
        header.createCell(7).setCellValue("Nombre de " +
                "Mort");
        header.createCell(8).setCellValue("Observations");

        int rowNum = 1 ;

        for(NaissanceDto naissance: data){
            final Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(naissance.nomIntervenant());
            row.createCell(1).setCellValue(String.valueOf(naissance.fermeId()));
            row.createCell(2).setCellValue( naissance.telephoneIntervenant());
            row.createCell(3).setCellValue(naissance.observations());
            row.createCell(4).setCellValue(naissance.dateNaissance().toString());
            row.createCell(5).setCellValue( naissance.croisementId().toString());
            row.createCell(6).setCellValue(String.valueOf(naissance.nbVivant()));
            row.createCell(7).setCellValue(naissance.nbMort());
            row.createCell(8).setCellValue(naissance.observations());
        }

        final ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }


}

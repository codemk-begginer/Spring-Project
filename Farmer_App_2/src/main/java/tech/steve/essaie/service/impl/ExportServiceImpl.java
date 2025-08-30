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
import tech.steve.essaie.config.ExcelExportUtil;
import tech.steve.essaie.config.PdfExportUtil;
import tech.steve.essaie.dto.AnimalDto;
import tech.steve.essaie.dto.NaissanceDto;
import tech.steve.essaie.dto.TransactionDto;
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

    private final PdfExportUtil pdfExportUtil;
    private final ExcelExportUtil excelExportUtil;

    public TransactionDto toDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setType(transaction.getType());
        dto.setCategorie(transaction.getCategorie());
        dto.setMontant(transaction.getMontant());
        dto.setDate(transaction.getDate());
        dto.setDescription(transaction.getDescription());
        dto.setNomIntervenant(transaction.getNomIntervenant());
        dto.setTelephoneIntervenant(transaction.getTelephoneIntervenant());

        // récupérer seulement les IDs des relations
        dto.setAnimalId(transaction.getAnimal() != null ? transaction.getAnimal().getId() : null);
        dto.setFermeId(transaction.getFerme() != null ? transaction.getFerme().getId() : null);

        return dto;
    }


    @Override
    public void exportTransactionsToPdf(HttpServletResponse response) throws IOException {
        List<TransactionDto> data = transactionRepository.findAll()
                .stream()
                .map(this::toDto)
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
            table.addCell(transaction.getType().toString());
            table.addCell(transaction.getCategorie());
            table.addCell(transaction.getMontant().toString());
            table.addCell(transaction.getDate().toString());
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getAnimalId().toString());
            table.addCell(transaction.getNomIntervenant());
            table.addCell(transaction.getTelephoneIntervenant());
            table.addCell(transaction.getFermeId().toString());


        }

        // Ajouter le tableau au document
        document.add(table);

        document.close();
    }

    @Override
    public void exportTransactionsToExcel(HttpServletResponse response) throws IOException {
        List<TransactionDto> data = transactionRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
                final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Users");

        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
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
            row.createCell(0).setCellValue(transaction.getId());
            row.createCell(1).setCellValue(transaction.getType().ordinal());
            row.createCell(2).setCellValue(transaction.getCategorie());
            row.createCell(3).setCellValue( transaction.getMontant().toString());
            row.createCell(4).setCellValue(transaction.getDate());
            row.createCell(5).setCellValue(transaction.getDescription());
            row.createCell(6).setCellValue( transaction.getAnimalId().toString());
            row.createCell(7).setCellValue(transaction.getNomIntervenant());
            row.createCell(8).setCellValue(transaction.getTelephoneIntervenant());
            row.createCell(9).setCellValue( transaction.getFermeId().toString());
        }

        final ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public AnimalDto toDto(Animal animal) {
        AnimalDto dto = new AnimalDto();
        dto.setId(animal.getId());
        dto.setCode(animal.getCode());
        dto.setFermeId(animal.getFerme().getId());
        dto.setGeneration(animal.getGeneration());
        dto.setSexe(animal.getSexe());
        dto.setDateNaissance(animal.getDateNaissance());
        dto.setDateMort(animal.getDateMort());
        dto.setQrCodeUrl(animal.getQrCodeUrl());
        dto.setObservations(animal.getObservations());
        dto.setStatut(animal.getStatut());
        dto.setNom(animal.getNom());
        if (animal.getMere() != null) {
            dto.setMereId(animal.getMere().getId());
        }
          if (animal.getPere() != null) {
              dto.setPereId(animal.getPere().getId());
        }



        // récupérer seulement les IDs des relations
        dto.setMereId(animal.getMere() != null ? animal.getMere().getId() : null);
        dto.setPereId(animal.getPere() != null ? animal.getPere().getId() : null);
        dto.setFermeId(animal.getFerme() != null ? animal.getFerme().getId() : null);

        return dto;
    }

    @Override
    public void exportAnimauxToPdf(HttpServletResponse response) throws IOException {
       List<AnimalDto> data = animalRepository.findAll()
                .stream()
                .map(this::toDto)
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
        PdfPTable table = new PdfPTable(11); // 10 colonnes
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

        cell.setPhrase(new Phrase("Ferme", fontHeader));
        table.addCell(cell);



        // Ajouter les données de la base de données
        for (AnimalDto animal : data) {
            table.addCell(animal.getId().toString());
            table.addCell(animal.getDateNaissance().toString());
            table.addCell(animal.getDateMort().toString());
            table.addCell(animal.getNom());
            table.addCell(animal.getGeneration().toString());
            if (animal.getMereId() != null) {
                table.addCell(animal.getMereId().toString());
            }else {
                table.addCell("");
            }
             if (animal.getPereId() != null) {
                table.addCell(animal.getPereId().toString());
            }else {
                 table.addCell("");
             }

            table.addCell(animal.getStatut().toString());
            table.addCell(animal.getSexe().toString());
            table.addCell(animal.getObservations());
            table.addCell(animal.getFermeId().toString());

        }

        // Ajouter le tableau au document
        document.add(table);

        document.close();


    }

    @Override
    public void exportAnimauxToExcel(HttpServletResponse response) throws IOException  {
            List<AnimalDto> data = animalRepository.findAll()
                    .stream()
                    .map(this::toDto)
                    .toList();
            final Workbook workbook = new XSSFWorkbook();
            final Sheet sheet = workbook.createSheet("Users");

            final Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("numero");
            header.createCell(1).setCellValue("nom");
            header.createCell(2).setCellValue("Generation");
            header.createCell(3).setCellValue("mere");
            header.createCell(4).setCellValue("pere");
            header.createCell(5).setCellValue("status");
            header.createCell(6).setCellValue("date de" +
                    "Naissance");
            header.createCell(7).setCellValue("date de" +
                    "Mort");
            header.createCell(8).setCellValue("Observations");
            header.createCell(9).setCellValue("sexe");
            header.createCell(10).setCellValue("ferme");

            int rowNum = 1 ;

            for(AnimalDto animal: data){
                final Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(animal.getId());
                row.createCell(1).setCellValue(animal.getNom());
                row.createCell(2).setCellValue(animal.getGeneration());
                if (animal.getMereId() != null) {
                    row.createCell(3).setCellValue(animal.getMereId());
                }
                if (animal.getPereId() != null) {
                    row.createCell(4).setCellValue( animal.getPereId());
                }


                row.createCell(5).setCellValue(animal.getStatut().toString());
                row.createCell(6).setCellValue( animal.getDateNaissance().toString());
                row.createCell(7).setCellValue(animal.getDateMort().toString());
                row.createCell(8).setCellValue(animal.getObservations());
                row.createCell(9).setCellValue(animal.getSexe().toString());
                row.createCell(10).setCellValue( animal.getFermeId().toString());
            }

            final ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
    }

    public NaissanceDto toDto(Naissance naissance) {
        NaissanceDto dto = new NaissanceDto();
        dto.setId(naissance.getId());
        dto.setNomIntervenant(naissance.getNomIntervenant());
        dto.setFermeId(naissance.getFerme().getId());
        dto.setTelephoneIntervenant(naissance.getTelephoneIntervenant());
        dto.setObservations(naissance.getObservations());
        dto.setDateNaissance(naissance.getDateNaissance());
        dto.setCroisementId(naissance.getCroisement().getId());
        dto.setNbVivant(naissance.getNbVivant());
        dto.setNbMort(naissance.getNbMort());


        // récupérer seulement les IDs des relations
        dto.setCroisementId(naissance.getCroisement() != null ? naissance.getCroisement().getId() : null);
        dto.setFermeId(naissance.getFerme() != null ? naissance.getFerme().getId() : null);

        return dto;
    }

    @Override
    public void exportNaissancesToPdf(HttpServletResponse response) throws IOException {
        List<NaissanceDto> data = naissanceRepository.findAll()
                .stream()
                .map(this::toDto)
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
            table.addCell(naissance.getId().toString());
            table.addCell(naissance.getNomIntervenant());
            table.addCell(naissance.getFermeId().toString());
            table.addCell(naissance.getTelephoneIntervenant());
            table.addCell(naissance.getObservations());
            table.addCell(naissance.getDateNaissance().toString());
            table.addCell(naissance.getCroisementId().toString());
            table.addCell(naissance.getNbVivant().toString());
            table.addCell(naissance.getNbMort().toString());

        }

        // Ajouter le tableau au document
        document.add(table);

        document.close();

    }

    @Override
    public void exportNaissancesToExcel(HttpServletResponse response) throws IOException  {
        List<NaissanceDto> data = naissanceRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("Users");

        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("numero");
        header.createCell(1).setCellValue("nom de" +
                "l'intervenant");
        header.createCell(2).setCellValue("Ferme");
        header.createCell(3).setCellValue("Telephone de " +
                "l'intervenant");
        header.createCell(4).setCellValue("Observations");
        header.createCell(5).setCellValue("date de" +
                "Naissance");
        header.createCell(6).setCellValue("date de" +
                "Naissance");
        header.createCell(6).setCellValue("Croisement" );
        header.createCell(7).setCellValue("Nombre de " +
                "Vivant");
        header.createCell(8).setCellValue("Nombre de " +
                "Mort");

        int rowNum = 1 ;

        for(NaissanceDto naissance: data){
            final Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(naissance.getId());
            row.createCell(1).setCellValue(naissance.getNomIntervenant());
            row.createCell(2).setCellValue(naissance.getFermeId());
            row.createCell(3).setCellValue( naissance.getTelephoneIntervenant());
            row.createCell(4).setCellValue(naissance.getObservations());
            row.createCell(5).setCellValue(naissance.getDateNaissance().toString());
            row.createCell(6).setCellValue( naissance.getDateNaissance().toString());
            row.createCell(7).setCellValue(naissance.getCroisementId());
            row.createCell(8).setCellValue(naissance.getNbVivant());
            row.createCell(9).setCellValue(naissance.getNbMort().toString());
        }

        final ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }


}

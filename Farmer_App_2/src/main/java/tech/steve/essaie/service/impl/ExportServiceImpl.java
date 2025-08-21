package tech.steve.essaie.service.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.RichTextString;
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

    @Override
    public void exportTransactionsToPdf(HttpServletResponse response) throws IOException {
        List<Transaction> data = transactionRepository.findAll();
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
        PdfPTable table = new PdfPTable(10); // 10 colonnes pour ID, Nom, Sexe
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Ajouter les en-têtes de colonnes
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHeader.setColor(new Color(0,0,0));


        cell.setPhrase(new Phrase("TypeTransaction", fontHeader));
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
        cell.setPhrase(new Phrase("NomIntervenant", fontHeader));
        table.addCell(cell);
        cell.setPhrase(new Phrase("TelephoneIntervenant", fontHeader));
        table.addCell(cell);
           cell.setPhrase(new Phrase("Ferme", fontHeader));
        table.addCell(cell);



        // Ajouter les données de la base de données
        for (Transaction transaction : data) {
            table.addCell(transaction.getType().toString());
            table.addCell(transaction.getCategorie());
              table.addCell(transaction.getMontant().toString());
            table.addCell(transaction.getDate().toString());
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getAnimal().toString());
            table.addCell(transaction.getNomIntervenant());
            table.addCell(transaction.getTelephoneIntervenant());
            table.addCell(transaction.getFerme().toString());


        }

        // Ajouter le tableau au document
        document.add(table);

        document.close();
    }

    @Override
    public void exportTransactionsToExcel(HttpServletResponse response) throws IOException {
        List<Transaction> data = transactionRepository.findAll();
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

        for(Transaction transaction: data){
            final Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getId());
            row.createCell(1).setCellValue(transaction.getType().ordinal());
            row.createCell(2).setCellValue(transaction.getCategorie());
            row.createCell(3).setCellValue( transaction.getMontant().toString());
            row.createCell(4).setCellValue(transaction.getDate());
            row.createCell(5).setCellValue(transaction.getDescription());
            row.createCell(6).setCellValue( transaction.getAnimal().toString());
            row.createCell(7).setCellValue(transaction.getNomIntervenant());
            row.createCell(8).setCellValue(transaction.getTelephoneIntervenant());
            row.createCell(9).setCellValue( transaction.getFerme().toString());
        }

        final ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public void exportAnimauxToPdf(HttpServletResponse response) throws IOException {
       List<Animal> data = animalRepository.findAll();
//
//        Document document = new Document(PageSize.A4);
//        PdfWriter.getInstance(document,response.getOutputStream());
//        document.open();
//        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        fontTitle.setSize(18);
//
//        Paragraph paragraph = new Paragraph("Liste des Animaux" , fontTitle);
//        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
//
//        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
//        fontParagraph.setSize(12);
//
//        Paragraph paragraph2 = new Paragraph("This is the paragraph",fontParagraph);
//
//        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
//
//        document.add(paragraph);
//        document.add(paragraph2);
//
//        document.close();



    }

    @Override
    public byte[] exportAnimauxToExcel() {
        return new byte[0];
    }

//    @Override
//    public byte[] exportAnimauxToExcel() {
//        List<Animal> data = animalRepository.findAll();
//        return excelExportUtil.generateAnimauxExcel(data);
//    }

    @Override
    public byte[] exportNaissancesToPdf() {
        List<Naissance> data = naissanceRepository.findAll();
        return pdfExportUtil.generateNaissancesPdf(data);
    }

    @Override
    public byte[] exportNaissancesToExcel() {
        return new byte[0];
    }

//    @Override
//    public byte[] exportNaissancesToExcel() {
//        List<Naissance> data = naissanceRepository.findAll();
//        return excelExportUtil.generateNaissancesExcel(data);
//    }
}

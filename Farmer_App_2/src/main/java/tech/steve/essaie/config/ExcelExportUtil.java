package tech.steve.essaie.config;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExcelExportUtil {
//
//    private  final TransactionRepository transactionRepository;
//
//    public byte[] generateTransactionsExcel(List<Transaction> data)  {
//        // TODO: Implémenter avec Apache POI
//
//
//        return this.exportExcel(response);
//    }
//
//    public byte[] generateAnimauxExcel(List<Animal> data) {
//        // TODO: Implémenter
//        return new byte[0];
//    }
//
//    public byte[] generateNaissancesExcel(List<Naissance> data) {
//        // TODO: Implémenter
//        return new byte[0];
//    }
//
//
//
//
//    public byte[] exportExcel() throws IOException {
//        List<Transaction> transactions = this.transactionRepository.findAll();
//
//           Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("Users");
//
//            Row header = sheet.createRow(0);
//            header.createCell(0).setCellValue("ID");
//            header.createCell(1).setCellValue("typeTransaction");
//            header.createCell(2).setCellValue("categorie");
//            header.createCell(3).setCellValue("montant");
//            header.createCell(4).setCellValue("date");
//            header.createCell(5).setCellValue("description");
//            header.createCell(6).setCellValue("animal");
//            header.createCell(7).setCellValue("nomIntervenant");
//            header.createCell(8).setCellValue("TelephoneIntervenant");
//            header.createCell(9).setCellValue("ferme");
//
//            int rowNum = 1;
//
//            for (Transaction transaction : transactions) {
//                Row row = sheet.createRow(rowNum++);
//                row.createCell(0).setCellValue(transaction.getId());
//                row.createCell(1).setCellValue(transaction.getType().ordinal());
//                row.createCell(2).setCellValue(transaction.getCategorie());
//                row.createCell(3).setCellValue(transaction.getMontant().doubleValue()); // si montant est numérique
//                row.createCell(4).setCellValue(transaction.getDate().toString()); // si c'est LocalDate
//                row.createCell(5).setCellValue(transaction.getDescription());
//                row.createCell(6).setCellValue(transaction.getAnimal().toString()); // adapter selon le type
//                row.createCell(7).setCellValue(transaction.getNomIntervenant());
//                row.createCell(8).setCellValue(transaction.getTelephoneIntervenant());
//                row.createCell(9).setCellValue(transaction.getFerme().toString()); // adapter selon le type
//            }
//
//            // écrire dans un flux mémoire
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            workbook.write(out);
//
//            return out.toByteArray();
//        }
//    }
//
//
//    public  void exportExcel(HttpServletResponse response) throws IOException {
//        final List<Transaction> transactions = this.transactionRepository.findAll();
//
//        final Workbook workbook = new XSSFWorkbook();
//        final Sheet sheet = workbook.createSheet("Users");
//
//        final Row header = sheet.createRow(0);
//        header.createCell(0).setCellValue("ID");
//        header.createCell(1).setCellValue("typeTransaction");
//        header.createCell(2).setCellValue("categorie");
//        header.createCell(3).setCellValue("montant");
//        header.createCell(4).setCellValue("date");
//        header.createCell(5).setCellValue("description");
//        header.createCell(6).setCellValue("animal");
//        header.createCell(7).setCellValue("nomIntervennt");
//        header.createCell(8).setCellValue("TelephoneIntervenant");
//        header.createCell(9).setCellValue("ferme");
//
//        int rowNum = 1 ;
//
//        for(Transaction transaction: transactions){
//            final Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(transaction.getId());
//            row.createCell(1).setCellValue(transaction.getType().ordinal());
//            row.createCell(2).setCellValue(transaction.getCategorie());
//            row.createCell(3).setCellValue((RichTextString) transaction.getMontant());
//            row.createCell(4).setCellValue(transaction.getDate());
//            row.createCell(5).setCellValue(transaction.getDescription());
//            row.createCell(6).setCellValue((RichTextString) transaction.getAnimal());
//            row.createCell(7).setCellValue(transaction.getNomIntervenant());
//            row.createCell(8).setCellValue(transaction.getTelephoneIntervenant());
//            row.createCell(9).setCellValue((RichTextString) transaction.getFerme());
//        }
//
//        final ServletOutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
//
//    }
}

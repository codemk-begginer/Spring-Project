package tech.steve.essaie.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ExportService {
    public void exportTransactionsToPdf(HttpServletResponse response) throws IOException;
    void exportTransactionsToExcel(HttpServletResponse response) throws IOException;

    public void exportAnimauxToPdf(HttpServletResponse response) throws IOException;
    byte[] exportAnimauxToExcel();

    byte[] exportNaissancesToPdf();
    byte[] exportNaissancesToExcel();
}

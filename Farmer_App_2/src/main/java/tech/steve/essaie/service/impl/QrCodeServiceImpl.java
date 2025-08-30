package tech.steve.essaie.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.steve.essaie.model.Animal;
import tech.steve.essaie.model.QRCode;
import tech.steve.essaie.repository.QRCodeRepository;
import tech.steve.essaie.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@AllArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {


    @Override
    public String genererQrCodePourAnimal(String codeAnimal) {
        String content = "https://tonapp.com/animal/" + codeAnimal;

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 250, 250);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            String qr = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

//            log.info(content);
//            log.info(qr);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);

        } catch (WriterException | IOException e) {
            throw new RuntimeException("Erreur lors de la génération du QR code", e);
        }



    }

//    @Override
//    public String genererQrCodePourAnimal(Animal animal) {
//        String content = "https://tonapp.com/animal/" + animal.getId();
//
//        try {
//            QRCodeWriter writer = new QRCodeWriter();
//            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 250, 250);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
//            byte[] imageBytes = baos.toByteArray();
//            return content;
//            //return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
//        } catch (WriterException | IOException e) {
//            throw new RuntimeException("Erreur lors de la génération du QR code", e);
//        }
//    }


}
package com.example.demo;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Controller // Tells Spring: "I handle web stuff"
public class QrCodeController {

    @GetMapping("/") // Show the form when visiting "/"
    public String showForm() {
        return "index"; // Loads index.html (we’ll make this next)
    }

    @PostMapping("/generate-qr") // When form is submitted
    public String generateQrCode(@RequestParam("link") String link, Model model) 
            throws WriterException, IOException {
        // QR code size (300x300 pixels)
        int width = 300;
        int height = 300;

        // Generate QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, width, height);

        // Convert QR code to image bytes
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] qrCodeBytes = pngOutputStream.toByteArray();

        // Convert to Base64 so HTML can display it
        String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);

        // Send QR code to front-end
        model.addAttribute("qrCodeImage", "data:image/png;base64," + qrCodeBase64);
        model.addAttribute("link", link); // Keep the link to show it
        return "index"; // Reload the same page with QR code
    }
}
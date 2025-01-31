package com.hotel_management.service;

import com.hotel_management.entity.Bookings;
import com.hotel_management.entity.Property;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PdfService {

    private static final String FILE_DIRECTORY = "D:\\hotel managment system\\pdfs\\"; // Change as needed

    public String generateAndSavePdf(Property property, Bookings bookings) {
        // Create unique file name with timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "property_" + bookings.getId() + "_"+".pdf";
        String filePath = FILE_DIRECTORY + fileName;

        Document document = new Document();
        try {
            File file = new File(FILE_DIRECTORY);
            file.mkdirs(); // Ensure directory exists

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Add property details to PDF
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            table.addCell("Property Attribute");
            table.addCell("Value");

            table.addCell("Property ID");
            table.addCell(String.valueOf(property.getId()));

            table.addCell("Property Name");
            table.addCell(property.getName());

            table.addCell("Property City");
            table.addCell(property.getCity().toString());

            table.addCell("Property Country");
            table.addCell(property.getCountry().toString());

            table.addCell("No. of Bathrooms");
            table.addCell(String.valueOf(property.getNo_of_bathrooms()));

            table.addCell("No. of Beds");
            table.addCell(String.valueOf(property.getNo_of_beds()));

            table.addCell("No. of Guests");
            table.addCell(String.valueOf(property.getNo_of_guest()));

            table.addCell("Generated at");
            table.addCell(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            document.add(table);
            document.close();

            return "PDF saved successfully at: " + filePath;
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}

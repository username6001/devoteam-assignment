package com.devoteam.barcode;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ApplicationScoped
public class BarcodeService {

    @Inject
    private EntityManager em;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Barcode> getBarcodes() {
        return em.createQuery("from Barcode order by expiryDate", Barcode.class).getResultList();
    }

    private Barcode csvStringToBarcode(String[] csvLine) {
        Barcode barcode = new Barcode();
        // Barcode,Category,ItemName,SellingPrice,ManufacturingDate,ExpiryDate,Quantity
        barcode.barcode = csvLine[0];
        barcode.category = csvLine[1];
        barcode.itemName = csvLine[2];
        barcode.sellingPrice = csvLine[3] == null ? null : new BigDecimal(csvLine[3]);
        barcode.manufacturingDate = csvLine[4] == null ? null : LocalDate.parse(csvLine[4], formatter);
        barcode.expiryDate = csvLine[5] == null ? null : LocalDate.parse(csvLine[5], formatter);
        barcode.quantity = csvLine[6] == null ? 0 : Long.parseLong(csvLine[6]);
        return barcode;
    }

    public void storeCsvFile(FileUpload file) {
        try (Reader reader = Files.newBufferedReader(file.uploadedFile());
             CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                // skip header
                if ("Barcode".equals(line[0])) {
                    continue;
                }
                Barcode barcode = csvStringToBarcode(line);
                em.persist(barcode);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

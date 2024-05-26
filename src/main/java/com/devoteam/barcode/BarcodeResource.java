package com.devoteam.barcode;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/api/v1/barcodes")
public class BarcodeResource {

    @Inject
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Barcode> get() {
        return em.createQuery("from Barcode", Barcode.class).getResultList();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/upload-csv")
    @Transactional
    public String uploadCsv(@FormParam("file") FileUpload file) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            List<List<String>> list = new ArrayList<>();
            List<Barcode> barcodes = new ArrayList<>();
            try (Reader reader = Files.newBufferedReader(file.uploadedFile())) {
                try (CSVReader csvReader = new CSVReader(reader)) {
                    String[] line;
                    while ((line = csvReader.readNext()) != null) {
                        // skip header
                        if ("Barcode".equals(line[0])) {
                            continue;
                        }
                        Barcode barcode = new Barcode();
                        // Barcode,Category,ItemName,SellingPrice,ManufacturingDate,ExpiryDate,Quantity
                        barcode.barcode = line[0];
                        barcode.category = line[1];
                        barcode.itemName = line[2];
                        barcode.sellingPrice = line[3] == null ? null : new BigDecimal(line[3]);
                        barcode.manufacturingDate = line[4] == null ? null : LocalDate.parse(line[4], formatter);
                        barcode.expiryDate = line[5] == null ? null : LocalDate.parse(line[5], formatter);
                        barcode.quantity = line[6] == null ? 0 : Long.parseLong(line[6]);
                        em.persist(barcode);
                        barcodes.add(barcode);
                        list.add(Arrays.asList(line));
                    }
                } catch (CsvValidationException e) {
                    throw new RuntimeException(e);
                }
            }
            return barcodes.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/echo2")
    public String echo2(@FormParam("file") FileUpload file) {
        try {
            return Files.readString(file.uploadedFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

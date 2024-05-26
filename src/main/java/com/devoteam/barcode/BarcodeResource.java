package com.devoteam.barcode;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/api/v1/barcodes")
public class BarcodeResource {

    @GET
    public String get() {
        return "Works!";
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/upload-csv")
    public String uploadCsv(@FormParam("file") FileUpload file) {
        try {
            List<List<String>> list = new ArrayList<>();
            try (Reader reader = Files.newBufferedReader(file.uploadedFile())) {
                try (CSVReader csvReader = new CSVReader(reader)) {
                    String[] line;
                    while ((line = csvReader.readNext()) != null) {
                        // skip header
                        if ("Barcode".equals(line[0])) {
                            continue;
                        }
                        list.add(Arrays.asList(line));
                    }
                } catch (CsvValidationException e) {
                    throw new RuntimeException(e);
                }
            }
            return list.toString();
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

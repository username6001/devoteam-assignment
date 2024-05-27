package com.devoteam.barcode;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    private BarcodeService barcodeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public List<Barcode> get() {
        return barcodeService.getBarcodes();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/upload-csv")
    @Transactional
    public Response uploadCsv(@FormParam("file") FileUpload file) {
        barcodeService.storeCsvFile(file);
        return Response.ok().build();
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

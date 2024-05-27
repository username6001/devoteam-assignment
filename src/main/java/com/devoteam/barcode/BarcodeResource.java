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
    @Path("/all")
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Barcode getBarcode(@QueryParam("barcode") String barcode) {
        return barcodeService.getBarcode(barcode);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Barcode deleteBarcode(@QueryParam("barcode") String barcode) {
        return barcodeService.deleteBarcode(barcode);
    }
}

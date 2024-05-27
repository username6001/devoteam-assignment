package com.devoteam.barcode;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.multipart.FileUpload;

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
    public Barcode getBarcode(@QueryParam("barcode") String barcodeStr) {
        Barcode barcode = barcodeService.getBarcode(barcodeStr);
        if (barcode == null) {
            throw new NotFoundException();
        }
        return barcode;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Barcode deleteBarcode(@QueryParam("barcode") String barcode) {
        return barcodeService.deleteBarcode(barcode);
    }
}

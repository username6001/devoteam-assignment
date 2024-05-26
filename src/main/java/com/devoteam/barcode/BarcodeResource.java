package com.devoteam.barcode;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;

@Path("/api/v1/barcodes")
public class BarcodeResource {

    @GET
    public String get() {
        return "Works!";
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/echo")
    public String echo(String requestBody) {
        return requestBody;
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

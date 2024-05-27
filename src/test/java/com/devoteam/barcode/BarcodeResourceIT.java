package com.devoteam.barcode;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class BarcodeResourceIT {

    private static final String BARCODE_URL = "/api/v1/barcodes/";

    @Test
    @Order(1)
    void testGetAllBarcodesIsEmpty() {
        given()
                .when().get(BARCODE_URL + "all")
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    @Order(2)
    void uploadCsv() {
        given()
                .multiPart("file", "barcodes.csv", getClass().getClassLoader().getResourceAsStream("barcodes.csv"))
                .when().post(BARCODE_URL + "upload-csv")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    void testGetAllBarcodes() {
        JsonPath response = given()
                .when().get(BARCODE_URL + "all")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        Object responseObj = response.get();
        assertThat(responseObj, instanceOf(List.class));
        assertThat(((List)responseObj).get(0), instanceOf(Map.class));
        List<Map> responseList = (List<Map>) responseObj;
        assertThat(responseList.size(), is(3));
        // 92140,Dairy,Cheese,3.49,2022-12-10,2023-01-10,120
        Map first = responseList.get(0);
        assertThat(first.size(), is(7));
        assertThat(first.get("barcode"), is("92140"));
        assertThat(first.get("category"), is("Dairy"));
        assertThat(first.get("itemName"), is("Cheese"));
        assertThat(first.get("sellingPrice"), is(3.49F));
        assertThat(first.get("manufacturingDate"), is("2022-12-10"));
        assertThat(first.get("expiryDate"), is("2023-01-10"));
        assertThat(first.get("quantity"), is(120));
        // 75930,Dairy,Milk,2.99,2023-01-01,2023-01-15,100
        Map second = responseList.get(1);
        assertThat(second.get("expiryDate"), is("2023-01-15"));
        // 84035,Dairy,Yogurt,1.79,2023-02-05,2023-02-25,80
        Map third = responseList.get(2);
        assertThat(third.get("expiryDate"), is("2023-02-25"));
    }

    @Test
    @Order(4)
    void testGetBarcode() {
        JsonPath response = given()
                .when()
                .queryParam("barcode", "92140")
                .get(BARCODE_URL)
                .then()
                .statusCode(200)
                .extract().jsonPath();
        Map responseMap = response.get();
        assertThat(responseMap.get("barcode"), is("92140"));
        assertThat(responseMap.get("category"), is("Dairy"));
        assertThat(responseMap.get("itemName"), is("Cheese"));
        assertThat(responseMap.get("sellingPrice"), is(3.49F));
        assertThat(responseMap.get("manufacturingDate"), is("2022-12-10"));
        assertThat(responseMap.get("expiryDate"), is("2023-01-10"));
        assertThat(responseMap.get("quantity"), is(120));
    }

    @Test
    @Order(5)
    void deleteBarcode() {
        given()
                .when()
                .queryParam("barcode", "92140")
                .delete(BARCODE_URL)
                .then()
                .statusCode(200);
        given()
                .when()
                .queryParam("barcode", "92140")
                .get(BARCODE_URL)
                .then()
                .statusCode(404);
        JsonPath response = given()
                .when()
                .get(BARCODE_URL + "all")
                .then()
                .statusCode(200)
                .extract().jsonPath();
        assertThat(((List)response.get()).size(), is(2));
    }
}

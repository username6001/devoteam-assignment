package com.devoteam.barcode;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "barcodes",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"barcode"})
})
public class Barcode {

    @Id
    @Column(nullable = false)
    public String barcode;

    public String category;

    public String itemName;

    public BigDecimal sellingPrice;

    public Date manufacturingDate;

    public Date expiryDate;

    public long quantity;

    @Version
    public int version;
}

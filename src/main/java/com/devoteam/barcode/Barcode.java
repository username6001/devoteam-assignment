package com.devoteam.barcode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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

    public LocalDate manufacturingDate;

    public LocalDate expiryDate;

    public long quantity;

    @JsonIgnore
    @Version
    public int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Barcode barcode1)) return false;
        return quantity == barcode1.quantity && version == barcode1.version && Objects.equals(barcode, barcode1.barcode) && Objects.equals(category, barcode1.category) && Objects.equals(itemName, barcode1.itemName) && Objects.equals(sellingPrice, barcode1.sellingPrice) && Objects.equals(manufacturingDate, barcode1.manufacturingDate) && Objects.equals(expiryDate, barcode1.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, category, itemName, sellingPrice, manufacturingDate, expiryDate, quantity, version);
    }

    @Override
    public String toString() {
        return "Barcode{" +
                "barcode='" + barcode + '\'' +
                ", category='" + category + '\'' +
                ", itemName='" + itemName + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", manufacturingDate=" + manufacturingDate +
                ", expiryDate=" + expiryDate +
                ", quantity=" + quantity +
                ", version=" + version +
                '}';
    }
}

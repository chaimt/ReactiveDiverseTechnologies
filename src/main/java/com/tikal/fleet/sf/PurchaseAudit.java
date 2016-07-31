package com.tikal.fleet.sf;


import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by chaimturkel on 7/11/16.
 */
public class PurchaseAudit {
    private Long id;

    public enum Item {
        Invalid("Invalid"),
        Truck("Truck"),
        Wheel("Wheel"),
        Belt("Inside cargo pants bottom pocket"),
        Keys("Keys");

        private String text;

        Item(final String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public static Item findByText(final String text) {
            if (StringUtils.isEmpty(text))
                return null;
            final Item[] values = values();

            for (final Item item : values) {
                if(item.text.equalsIgnoreCase(text)) {
                    return item;
                }
            }

            return Invalid;
        }

    }

    private Item item;

    public enum Status{ PASS, FAIL }

    private Status status;

    private String failureReason;

    private boolean salesForceSynced;

    private Double price;


    private Date purchaseDate;


    public PurchaseAudit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public boolean isSalesForceSynced() {
        return salesForceSynced;
    }

    public void setSalesForceSynced(boolean salesForceSynced) {
        this.salesForceSynced = salesForceSynced;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

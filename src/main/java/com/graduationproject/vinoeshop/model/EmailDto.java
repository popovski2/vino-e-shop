package com.graduationproject.vinoeshop.model;

import java.time.LocalDateTime;
import java.util.List;

public class EmailDto {


    private LocalDateTime date;
    private String buyerEmail;
    private String buyerName;
    private List<Wine> wines;
    private String address;
    private Double price;

    public EmailDto(LocalDateTime date, String buyerEmail, String buyerName, List<Wine> wines, Double price) {
        this.date = date;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.wines = wines;
        this.price = price;
    }

    public EmailDto(LocalDateTime date, String buyerEmail, String buyerName, List<Wine> wines, String address) {
        this.date = date;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.wines = wines;
        this.address = address;
    }


    public EmailDto(LocalDateTime date, String buyerEmail, String buyerName, List<Wine> wines) {
        this.date = date;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.wines = wines;
    }

    public EmailDto() {
    }

    /** GETTERS **/
    public LocalDateTime getDate() {
        return date;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public List<Wine> getWines() {
        return wines;
    }

    public Double getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    /** SETTERS **/
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setWines(List<Wine> wines) {
        this.wines = wines;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

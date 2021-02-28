package br.ce.wcaquino.entidades;

import java.util.Calendar;

public class Invoice {

    private Calendar date;
    private String customer;
    private double amount;
    public Invoice(Calendar date, String customer, double amount) {
        this.date = date;
        this.customer = customer;
        this.amount = amount;
    }
    public Calendar getDate() {
        return date;
    }
    public String getCustomer() {
        return customer;
    }
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "customer='" + customer + '\'' +
                ", amount=" + amount +
                '}';
    }
}

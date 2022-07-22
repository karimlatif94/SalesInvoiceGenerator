/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sales.model;

import java.util.ArrayList;

/**
 *
 * @author TOSHIBA
 */
public class InvoiceDetail {
    private InvoiceHeader invoiceHeader;
    private String itemName;    
    private double price;
    private int quantity;

   
    public InvoiceDetail(InvoiceHeader invoiceHeader, String itemName, double price, int quantity) {
        this.invoiceHeader = invoiceHeader;
        this.itemName = itemName;        
        this.price = price;
        this.quantity = quantity;
    }

    public InvoiceHeader getInvoiceHeader() {
        return invoiceHeader;
    }

    public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
        this.invoiceHeader = invoiceHeader;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
     public double getTotalPrice() {
        return getQuantity()*getPrice();
    }
    public String getDetailAsCSV() {
        return "" + getInvoiceHeader().getInvoiceNo()+ "," + getItemName() + "," + getPrice()+ "," + getQuantity();
    }
    @Override
    public String toString() {
        return "InvoiceDetail{" + "itemName=" + itemName + ", price=" + price + ", quantity=" + quantity + ", Total price=" + getTotalPrice() + '}';
    }
    
    
    
            
}

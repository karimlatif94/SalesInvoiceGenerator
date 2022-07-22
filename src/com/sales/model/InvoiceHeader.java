/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sales.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author TOSHIBA
 */
public class InvoiceHeader {
    private int invoiceNo;
    private Date invoiceDate;
    private String customerName;
    private ArrayList<InvoiceDetail> detailList;
    
    
    public InvoiceHeader(int invoiceNo, Date invoiceDate, String customerName) {
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }

    public ArrayList<InvoiceDetail> getDetailList() {
        if (detailList == null)
            detailList = new ArrayList<>();
        return detailList;
    }


    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    
    public double getInvoiceTotal() {
        double total = 0;
        for (InvoiceDetail detail : getDetailList())
            total +=detail.getTotalPrice();
        return total;
    }
    public void addDetailLine(InvoiceDetail detail) {
        getDetailList().add(detail);

    }

    public String getHeaderAsCSV() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getInvoiceNo() + "," + df.format(getInvoiceDate()) + "," + getCustomerName();
    }

    @Override
    public String toString() {
        String str= "InvoiceHeader{" + "invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", customerName=" + customerName + '}';
        for (InvoiceDetail detail : getDetailList()) {
            str += "\n\t" + detail;
        }
        return str;
    
    }
    
    
    
}

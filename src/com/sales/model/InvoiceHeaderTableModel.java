/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author TOSHIBA
 */
public class InvoiceHeaderTableModel extends AbstractTableModel {
    private ArrayList<InvoiceHeader> headerData;    
    private String[] columns = {"InvoiceNo", "Invoice Date", "Customer Name", "Invoice Total"};

    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> headerData) {
        this.headerData = headerData;
    }

    public ArrayList<InvoiceHeader> getHeaderData() {
        return headerData;
    }
    
    
    @Override
    public int getRowCount() {
        return headerData.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columns[column]; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader header = headerData.get(rowIndex);
        switch (columnIndex){
            case 0:
                return header.getInvoiceNo();
            case 1:
                return header.getInvoiceDate();
            case 2:
                return header.getCustomerName();
            case 3:
                return header.getInvoiceTotal();
        }
        return null;
    }
    
}

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
public class InvoiceDetailTableModel extends AbstractTableModel {
    private ArrayList<InvoiceDetail> detailData;
    private String[] columns = {"Item Name", "Price", "Quantity", "Total Price"};

    public InvoiceDetailTableModel(ArrayList<InvoiceDetail> detailData) {
        this.detailData = detailData;
    }

    public ArrayList<InvoiceDetail> getDetailData() {
        return detailData;
    }


    @Override
    public int getRowCount() {
        return detailData.size();
    }

    @Override
    public int getColumnCount() {
        
        return columns.length;
    }
    @Override
    public String getColumnName(int column) {
        return columns[column]; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceDetail invDetail = detailData.get(rowIndex);
        switch (columnIndex){
            case 0:
                return invDetail.getItemName();
            case 1:
                return invDetail.getPrice();
            case 2:
                return invDetail.getQuantity();
            case 3:
                return invDetail.getTotalPrice();
        }
        return null;
    }
    
}

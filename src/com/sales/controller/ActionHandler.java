/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sales.controller;

import com.sales.model.InvoiceDetail;
import com.sales.model.InvoiceDetailTableModel;
import com.sales.model.InvoiceHeader;
import com.sales.view.DetailDialog;
import com.sales.view.InvoiceDialog;
import com.sales.view.InvoiceFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author TOSHIBA
 */
public class ActionHandler implements ListSelectionListener, ActionListener{
    private InvoiceFrame frame;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    
    public ActionHandler(InvoiceFrame frame) {
        this.frame = frame;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       switch (e.getActionCommand()){
            case "Add Invoice":
                addInvoice();
                break;
            case "addInvoiceApproved":
                addInvoiceApproved();
                break;
            case "addInvoiceCancelled":
                addInvoiceCancelled();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Add Item":
                addItem();
                break;
            case "addItemApproved":
                addItemApproved();
                break;
            case "addItemCancelled":
                addItemCancelled();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "Load file":
                loadFile();
                break;
            case "Save file":
                saveFile();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
              
        int rowSelected = frame.getHeaderTable().getSelectedRow();
        
        if (rowSelected >= 0) {
            InvoiceHeader header = frame.getHeaderTableModel().getHeaderData().get(rowSelected);
            //populate labels
            frame.getInvoiceLbl().setText("" + header.getInvoiceNo());
            frame.getCustLbl().setText(df.format(header.getInvoiceDate()));
            frame.getDateLbl().setText("" + header.getCustomerName());
            frame.getTotalLbl().setText("" + header.getInvoiceTotal());
            
            ArrayList<InvoiceDetail> invDetails = header.getDetailList();            
            frame.setDetailTableModel(new InvoiceDetailTableModel(invDetails)); 
            frame.getDetailTable().setModel(frame.getDetailTableModel());  
            frame.getDetailTableModel().fireTableDataChanged();
        }      
        
    }

    private void addInvoice() {
        frame.setInvoiceDialog(new InvoiceDialog(frame));
        frame.getInvoiceDialog().setVisible(true);
    }
    private void addInvoiceApproved() {
        String custName = frame.getInvoiceDialog().getCustNameField().getText();
        String invDateStr = frame.getInvoiceDialog().getInvDateField().getText();
        hideInvoiceDialog();
        
        try {
            Date invoiceDate = df.parse(invDateStr);
            int invoiceNumber = getNewInvoiceNumber();
            InvoiceHeader invoiceHeader = new InvoiceHeader(invoiceNumber, invoiceDate, custName);
            frame.getLoadedHeaderList().add(invoiceHeader);
            frame.getHeaderTableModel().fireTableDataChanged();
            printInvoices();
        } catch (ParseException ex ) {
            JOptionPane.showMessageDialog(frame, "Wrong date Format, use this format (dd-MM-yyyy) ", "Error:"+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            
        }
    }

    private void addInvoiceCancelled() {
        hideInvoiceDialog();
        
    }
    private void hideInvoiceDialog() {
        frame.getInvoiceDialog().setVisible(false);
        frame.getInvoiceDialog().dispose();
        frame.setInvoiceDialog(null);
        
    }

    private void deleteInvoice() {
        
        int rowSelected = frame.getHeaderTable().getSelectedRow();  
        if (rowSelected >= 0) {
            frame.getHeaderTableModel().getHeaderData().remove(rowSelected);
            frame.getHeaderTableModel().fireTableDataChanged();
            frame.setDetailTableModel(new InvoiceDetailTableModel(new ArrayList<>()));
            frame.getDetailTable().setModel(frame.getDetailTableModel());
            frame.getDetailTableModel().fireTableDataChanged();
            frame.getCustLbl().setText("");
            frame.getDateLbl().setText("");
            frame.getInvoiceLbl().setText("");
            frame.getTotalLbl().setText("");
            printInvoices();

            JOptionPane.showMessageDialog(null, "Done: Invoice is deleted successfully!");
        }
        else
            JOptionPane.showMessageDialog(frame, "Error: Please select the invoice before deletion", "Error", JOptionPane.ERROR_MESSAGE);

    }

    private void addItem() {
        frame.setDetailDialog(new DetailDialog(frame));
        frame.getDetailDialog().setVisible(true);
    }
    
    private void addItemApproved() {
        try{
            String itemName = frame.getDetailDialog().getItemNameField().getText();
            String itemPriceStr = frame.getDetailDialog().getItemPriceField().getText();        
            String itemQtyStr = frame.getDetailDialog().getItemQtyField().getText();
            hideDetailDialog();                

            double itemPrice = Double.parseDouble(itemPriceStr);
            int itemQty = Integer.parseInt(itemQtyStr);
            int rowSelected = frame.getHeaderTable().getSelectedRow();
            if (rowSelected >= 0) {
                InvoiceHeader header = frame.getHeaderTableModel().getHeaderData().get(rowSelected);

                header.addDetailLine(new InvoiceDetail(header, itemName, itemPrice, itemQty));
                frame.getDetailTableModel().fireTableDataChanged();
                frame.getHeaderTableModel().fireTableDataChanged();
                frame.getTotalLbl().setText("" + header.getInvoiceTotal());

                printInvoices();
            }
            else
                JOptionPane.showMessageDialog(frame, "Error: Please select the invoice before adding a new item", "Error", JOptionPane.ERROR_MESSAGE);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(frame, "Error: Number format, Format:(Name: String, Qty:Integer , Price:Double)\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addItemCancelled() {
        hideDetailDialog();
    }
    private void hideDetailDialog() {
        frame.getDetailDialog().setVisible(false);
        frame.getDetailDialog().dispose();
        frame.setDetailDialog(null);
        
    }

    private void deleteItem() {
        int rowSelected = frame.getDetailTable().getSelectedRow();
        if (rowSelected >= 0) {
            InvoiceDetail detail = frame.getDetailTableModel().getDetailData().get(rowSelected);
            frame.getDetailTableModel().getDetailData().remove(rowSelected);            
            frame.getDetailTableModel().fireTableDataChanged();
            frame.getHeaderTableModel().fireTableDataChanged();
            frame.getTotalLbl().setText("" + detail.getInvoiceHeader().getInvoiceTotal());
            JOptionPane.showMessageDialog(null, "Done: Item is deleted successfully!");
            printInvoices();
        } else {
            JOptionPane.showMessageDialog(frame, "Error: Please select the item before deletion", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void loadFile() {
        try {
            JFileChooser filechooser = new JFileChooser();
            int choice = filechooser.showOpenDialog(frame);
            if (choice == JFileChooser.APPROVE_OPTION) {
                File headerFile = filechooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List<String> headerFileLines = Files.lines(headerPath).collect(Collectors.toList());
                ArrayList<InvoiceHeader> loadedHeaderList = new ArrayList();
                for(String line : headerFileLines)
                {
                    InvoiceHeader HeaderObj;
                    String[] splitedHeader = line.split(",");
                    if (splitedHeader.length != 0) {
                    
                    int invoiceNo = Integer.parseInt(splitedHeader[0]);
                    Date invoiceDate = new Date();
                    invoiceDate = df.parse(splitedHeader[1]);
                    HeaderObj = new InvoiceHeader(invoiceNo, invoiceDate, splitedHeader[2]);
                    loadedHeaderList.add(HeaderObj);
                         
                    }
                    
                }
                
                choice = filechooser.showOpenDialog(frame);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File detailFile = filechooser.getSelectedFile();
                    Path detailPath = Paths.get(detailFile.getAbsolutePath());
                    List<String> detailFileLines = Files.lines(detailPath).collect(Collectors.toList());
                    for (String line : detailFileLines) {
                        InvoiceDetail detailObj;
                        String[] splitedDetail = line.split(",");
                        if (splitedDetail.length != 0) {

                            int invoiceNo = Integer.parseInt(splitedDetail[0]);                            
                            InvoiceHeader header = getInvHeaderByNum(loadedHeaderList, invoiceNo);
                            double price = Double.parseDouble(splitedDetail[2]);
                            int qty = Integer.parseInt(splitedDetail[3]);
                            
                            detailObj = new InvoiceDetail(header, splitedDetail[1], price, qty);
                            header.getDetailList().add(detailObj);
                        }
                    }
                 frame.setLoadedHeaderList(loadedHeaderList);
                 printInvoices();
                }
            
            }
        //Error & Exception handling
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Wrong Date Format Error\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "File Not Found Error\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Number Format Error\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "File Read Error\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
    }

    private void saveFile() {
        String headers = "";
        String details = "";
        try {
            for (InvoiceHeader header : frame.getLoadedHeaderList()) {
                headers += header.getHeaderAsCSV() + "\n";
                for (InvoiceDetail detail : header.getDetailList()) {
                    details += detail.getDetailAsCSV() + "\n";
                }
            }
            //Save InvoiceHeaders
            JOptionPane.showMessageDialog(frame, "Select file to save InvoiceHeader data!", "Save file", JOptionPane.WARNING_MESSAGE);
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File headerFile = fileChooser.getSelectedFile();
                try {
                    FileWriter headerFW = new FileWriter(headerFile);
                    headerFW.write(headers);
                    headerFW.flush();
                    headerFW.close();
                    //Save InvoiceDetails
                    JOptionPane.showMessageDialog(frame, "Select file to save InvoiceDetails data!", "Save file", JOptionPane.WARNING_MESSAGE);

                    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File detailsFile = fileChooser.getSelectedFile();
                        FileWriter detailFW = new FileWriter(detailsFile);
                        detailFW.write(details);
                        detailFW.flush();
                        detailFW.close();
                    }
                    JOptionPane.showMessageDialog(frame, "Files are saved successfully!");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(frame, "Invoices are empty cannot be saved. Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private InvoiceHeader getInvHeaderByNum(ArrayList<InvoiceHeader> loadedHeaderList, int invoiceNo) {//for searching header by invNo
        for (InvoiceHeader header : loadedHeaderList){
            if (invoiceNo == header.getInvoiceNo())
                return header;
        }
        return null;
    }

    private int getNewInvoiceNumber() {
       int max = 0;
        for(InvoiceHeader header : frame.getLoadedHeaderList()) {
            if (header.getInvoiceNo()> max)
                max = header.getInvoiceNo();           
        }
        return max + 1;
    }

    private void printInvoices() {
       for (InvoiceHeader header :frame.getLoadedHeaderList()) {
             System.out.println(header);
         }
    } 
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sales.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author TOSHIBA
 */
public class DetailDialog extends JDialog{
    
    private JLabel itemNameLbl;
    private JTextField itemNameField;    
    private JLabel itemQtyLbl;
    private JTextField itemQtyField;    
    private JLabel itemPriceLbl;
    private JTextField itemPriceField;
    private JButton addBtn;
    private JButton cancelBtn;
    
    public DetailDialog(InvoiceFrame frame) {
        itemNameField = new JTextField(20);
        itemNameLbl = new JLabel("Item Name");
        
        itemQtyField = new JTextField(20);
        itemQtyLbl = new JLabel("Item Quantity");
        
        itemPriceField = new JTextField(20);
        itemPriceLbl = new JLabel("Item Price");
        
        addBtn = new JButton("Add");
        cancelBtn = new JButton("Cancel");
        
        addBtn.setActionCommand("addItemApproved");
        cancelBtn.setActionCommand("addItemCancelled");
        
        addBtn.addActionListener(frame.getHandler());
        cancelBtn.addActionListener(frame.getHandler());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLbl);
        add(itemNameField);
        add(itemQtyLbl);
        add(itemQtyField);
        add(itemPriceLbl);
        add(itemPriceField);
        add(addBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemQtyField() {
        return itemQtyField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }


    
}

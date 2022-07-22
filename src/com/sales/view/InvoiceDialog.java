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
public class InvoiceDialog extends JDialog{
    private JTextField custNameField;
    private JTextField invDateField;
    private JLabel custNameLbl;
    private JLabel dateLbl;
    private JButton addBtn;
    private JButton cancelBtn;

    public InvoiceDialog(InvoiceFrame frame) {

        custNameLbl = new JLabel("Customer Name:");
        custNameField = new JTextField(20);
        dateLbl = new JLabel("Invoice Date dd-MM-yyyy:");
        invDateField = new JTextField(20);
        addBtn = new JButton("Add");
        cancelBtn = new JButton("Cancel");
 
        addBtn.setActionCommand("addInvoiceApproved");
        cancelBtn.setActionCommand("addInvoiceCancelled");
        addBtn.addActionListener(frame.getHandler());
        cancelBtn.addActionListener(frame.getHandler());
        setLayout(new GridLayout(4, 2));
        
        add(dateLbl);
        add(invDateField);
        add(custNameLbl);
        add(custNameField);
        add(addBtn);
        add(cancelBtn);
        pack();
        
    }

    public JTextField getCustNameField() {
        return custNameField;
    }

    public JTextField getInvDateField() {
        return invDateField;
    }

    
    
}

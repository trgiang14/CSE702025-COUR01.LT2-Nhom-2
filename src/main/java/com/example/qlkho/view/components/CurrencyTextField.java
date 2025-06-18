package com.example.qlkho.view.components;

import com.example.qlkho.utils.CurrencyFormatter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class CurrencyTextField extends ModernTextField {
    private boolean isUpdating = false;
    
    public CurrencyTextField() {
        super();
        setupCurrencyFormatting();
    }
    
    public CurrencyTextField(String placeholder) {
        super(placeholder);
        setupCurrencyFormatting();
    }
    
    private void setupCurrencyFormatting() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isUpdating) {
                    SwingUtilities.invokeLater(() -> formatCurrency(e));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isUpdating) {
                    SwingUtilities.invokeLater(() -> formatCurrency(e));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!isUpdating) {
                    SwingUtilities.invokeLater(() -> formatCurrency(e));
                }
            }
        });
    }
    
    private void formatCurrency(DocumentEvent e) {
        if (isUpdating) return;
        
        try {
            Document doc = e.getDocument();
            String text = doc.getText(0, doc.getLength());
            
            if (text.isEmpty() || text.equals(".")) {
                return;
            }
            
            String numbersOnly = text.replaceAll("[^0-9]", "");
            
            if (!numbersOnly.isEmpty()) {
                isUpdating = true;
                
                double value = Double.parseDouble(numbersOnly);
                String formatted = CurrencyFormatter.formatCurrency(value);
                
                if (!formatted.equals(text)) {
                    int caretPosition = getCaretPosition();
                    setText(formatted);
                    
                    try {
                        setCaretPosition(Math.min(caretPosition, formatted.length()));
                    } catch (IllegalArgumentException ex) {
                        setCaretPosition(formatted.length());
                    }
                }
                
                isUpdating = false;
            }
        } catch (BadLocationException | NumberFormatException ex) {
        }
    }
    

    public double getCurrencyValue() {
        return CurrencyFormatter.parseCurrency(getText());
    }
    
 
    public void setCurrencyValue(double value) {
        isUpdating = true;
        setText(CurrencyFormatter.formatCurrency(value));
        isUpdating = false;
    }
    
  
    public void setCurrencyValue(String value) {
        try {
            double doubleValue = Double.parseDouble(value);
            setCurrencyValue(doubleValue);
        } catch (NumberFormatException e) {
            isUpdating = true;
            setText(value);
            isUpdating = false;
        }
    }
} 
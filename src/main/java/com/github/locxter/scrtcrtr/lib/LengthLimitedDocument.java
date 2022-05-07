package com.github.locxter.scrtcrtr.lib;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LengthLimitedDocument extends PlainDocument {
    // Attribute
    private int limit;

    // Constructor
    public LengthLimitedDocument(int limit) {
        super();
        this.limit = limit;
    }

    // Function to insert the newly entered string if possible
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null) {
            return;
        }
        if (getLength() + str.length() <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}
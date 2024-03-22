package com.ecotech.invoicelinkgen.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InvoiceNumberGenerator {
    private static int lastInvoiceNumber = 0;

    public static String generateInvoiceNumber() {
        // Increment lastInvoiceNumber for each new invoice
        lastInvoiceNumber++;

        // Generate date string in the format yyyyMMdd
        String dateString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Combine prefix, date, and incremental number
        return "INV-" +dateString.substring(1, 5)+ LinkGenerator.generateUniqueShortCode(5)  + String.format("%04d", lastInvoiceNumber);
    }

}

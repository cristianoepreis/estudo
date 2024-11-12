package com.ufbra.gerarpdf;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PrimeiroPDF {

    public PrimeiroPDF(String fraseAImprimir){

        Document documentPDF = new Document();

        try {
            PdfWriter.getInstance(documentPDF, new FileOutputStream("Relatorio1.pdf"));

            documentPDF.open();
            Paragraph paragrafoTeste = new Paragraph(fraseAImprimir);
            documentPDF.add(paragrafoTeste);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        documentPDF.close();
    }
}

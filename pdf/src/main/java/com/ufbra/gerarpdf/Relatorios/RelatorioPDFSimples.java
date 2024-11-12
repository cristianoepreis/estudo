package com.ufbra.gerarpdf.Relatorios;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.ufbra.gerarpdf.vendas.Venda;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class RelatorioPDFSimples implements Relatorio{

    private Venda venda;
    private Document documentPDF;
    private String caminhoRelatorio = "RelatorioSimplesPDF.pdf";

    public RelatorioPDFSimples(Venda venda){
        this.venda = venda;
        this.documentPDF = new Document();
        try {
            PdfWriter.getInstance(this.documentPDF, new FileOutputStream(caminhoRelatorio));
            this.documentPDF.open();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void gerarCabecalho() {
        Paragraph paragraphTitulo = new Paragraph();
        paragraphTitulo.setAlignment(Element.ALIGN_CENTER);
        paragraphTitulo.add(new Chunk("Relatorio Ementas", new Font(Font.HELVETICA, 24)));
        this.documentPDF.add(paragraphTitulo);
        this.documentPDF.add(new Paragraph(" "));

        Paragraph paragrafoData = new Paragraph();
        paragrafoData.setAlignment(Element.ALIGN_CENTER);
        paragrafoData.add(new Chunk(this.venda.getDataVenda().toString()));
        this.documentPDF.add(paragrafoData);

        this.documentPDF.add(new Paragraph(" "));
        this.documentPDF.add(new Paragraph(" "));

        Paragraph paragrafoCliente = new Paragraph();
        paragrafoCliente.setAlignment(Element.ALIGN_CENTER);
        paragrafoCliente.add(new Chunk("Cliente: " + this.venda.getNomeCliente(), new Font(Font.BOLD,16)));
        this.documentPDF.add(paragrafoCliente);

        Paragraph paragrafoSessao = new Paragraph("----------------------------------------------------------------");
        paragrafoSessao.setAlignment(Element.ALIGN_CENTER);
        this.documentPDF.add(paragrafoSessao);
        this.documentPDF.add(new Paragraph(" "));
    }

    @Override
    public void gerarCorpo() {

    }

    @Override
    public void gerarRodape() {

    }

    @Override
    public void imprimir() {
        if(this.documentPDF != null && this.documentPDF.isOpen()){
            this.documentPDF.close();
        }
    }
}

package com.ufbra.gerarpdf.pdf;

import com.ufbra.gerarpdf.Relatorios.Relatorio;
import com.ufbra.gerarpdf.Relatorios.RelatorioPDFSimples;
import com.ufbra.gerarpdf.vendas.Produto;
import com.ufbra.gerarpdf.vendas.Venda;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PdfApplication {

	public static void main(String[] args) {

		List<Produto> produtos = new ArrayList<Produto>();
		Venda venda = new Venda("Cristiano", produtos);

		venda.addProdutoAoCarrinho(new Produto("Agua", 2, 1.5));
		venda.addProdutoAoCarrinho(new Produto("Suco", 4, 2.5));
		venda.addProdutoAoCarrinho(new Produto("Refrigerante", 1, 5.5));

		Relatorio relatorioSimples = new RelatorioPDFSimples(venda);
		relatorioSimples.gerarCabecalho();
		relatorioSimples.gerarCorpo();
		relatorioSimples.gerarRodape();
		relatorioSimples.imprimir();
	}

}




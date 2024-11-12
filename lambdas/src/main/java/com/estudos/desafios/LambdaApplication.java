package com.estudos.desafios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

interface OperacaoMatematica{
    int caucular(int a, int b);
}

interface ManipuladorString{
    String processar(String str);
}

public class LambdaApplication {
    public static void main(String[] args) {
        OperacaoMatematica soma = new OperacaoMatematica() {
            @Override
            public int caucular(int a, int b) {
                return a + b;
            }
        };
        System.out.println("Soma sem Função Lambda: " + soma.caucular(10, 20));

        OperacaoMatematica somaFL = (a, b) -> a + b;
        System.out.println("Soma com Função Lambda: " + somaFL.caucular(10, 20));

        OperacaoMatematica subtracaoFL = (a, b) -> a - b;
        System.out.println("Subtracao com Função Lambda: " + subtracaoFL.caucular(10, 20));

        OperacaoMatematica multiplicacaoFL = (a, b) -> a * b;
        System.out.println("Multiplicação com Função Lambda: " + multiplicacaoFL.caucular(10, 20));

        OperacaoMatematica divisaoFL = (a, b) -> a / b;
        System.out.println("Divisao com Função Lambda: " + divisaoFL.caucular(40, 20));


        // String Manipulation

        ManipuladorString uperCase = new ManipuladorString() {
            @Override
            public String processar(String str) {
                return str.toUpperCase(Locale.ROOT);
            }
        };
        System.out.println("UperCase sem Função Lambda: " + uperCase.processar("Estudos de Java"));

        ManipuladorString uperCaseFL = str -> str.toUpperCase(Locale.ROOT);
        System.out.println("UperCase Com Função Lambda: " + uperCaseFL.processar("Estudos de Java"));

        ManipuladorString lowerCaseFL = str -> str.toLowerCase(Locale.ROOT);
        System.out.println("UperCase Com Função Lambda: " + lowerCaseFL.processar("Estudos de Java"));

        //Word filter without lambda
        List<String> palavras = Arrays.asList("Java", "Kotlin", "Python", "JavaScript", "C#", "C++");
        List<String> palavrasComFiltro = new ArrayList<>();
        for (String palavra : palavras) {
            if (palavra.length() > 5) {
                palavrasComFiltro.add(palavra);
            }
        }
        System.out.println("Palavras com mais de 5 letras sem Função Lambda: " + palavrasComFiltro);


        //Word filter with lambda
        List<String> palavrasFiltradasFL = palavras.stream().filter(p -> p.length() > 5).toList();
        System.out.println("Palavras com mais de 5 letras com Função Lambda: " + palavrasFiltradasFL);

        //Objetct filter lambda

        List<Pessoa> pessoas = Arrays.asList(
                new Pessoa("Alice", 25),
                        new Pessoa("Bob", 30),
                        new Pessoa("Charlie", 35),
                        new Pessoa("David", 40)
        );

        List<String> nomes = pessoas.stream().map(p -> p.getNome()).toList();
        List<Integer> idades = pessoas.stream().map(Pessoa::getIdade).toList();
        System.out.println("Nomes: " + nomes);
        System.out.println("Idades: " + idades);
    }
}
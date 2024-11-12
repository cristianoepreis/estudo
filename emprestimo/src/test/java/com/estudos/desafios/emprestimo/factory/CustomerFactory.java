package com.estudos.desafios.emprestimo.factory;

import com.estudos.desafios.emprestimo.domain.Customer;

public class CustomerFactory {
    public static Customer build(){
        return new Customer(25, "123.456.789-00", "name", 5000.0, "SP");
    }

    public static Customer build(Double income){
        return new Customer(25, "123.456.789-00", "name", income, "SP");
    }

    public static Customer build(int age){
        return new Customer(age, "123.456.789-00", "name", 5000.0, "SP");
    }

    public static Customer build(String location){
        return new Customer(25, "123.456.789-00", "name", 5000.0, location);
    }
}

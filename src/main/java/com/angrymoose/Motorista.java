package com.angrymoose;

public class Motorista {
    private String nome;
    private String cpf;
    private Veiculo veiculo;
    private FormaPagamento formaPagamento;

    public Motorista(String cpf,String nome, Veiculo veiculo, FormaPagamento formaPgto){
        this.nome = nome;
        this.cpf = cpf;
        this.veiculo = veiculo;
        this.formaPagamento = formaPgto; 
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public Veiculo getVeiculo(){
        return veiculo;
    }

    public FormaPagamento getFormaPgto(){
        return formaPagamento;
    }

}
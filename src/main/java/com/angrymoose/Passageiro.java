package com.angrymoose;

public class Passageiro {
    private String CPF;
    private String nome;
    private FormaPagamento formaPagamento;
    private String nroCartao;

    public Passageiro(String CPF, String nome, FormaPagamento formaPagamento, String nroCartao){
        this.CPF = CPF;
        this.nome = nome;
        this.formaPagamento = formaPagamento;
        this.nroCartao = nroCartao;
    }

    public String getCPF(){
        return CPF;
    }

    public String getNome(){
        return nome;
    }

    public FormaPagamento getFormaPagamento(){
        return formaPagamento;
    }

    public String getNroCartao(){
        return nroCartao;
    }


}
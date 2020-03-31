package com.angrymoose;

public class Veiculo
{
    private String placa;
    private String marca;
    private String cor;
    private Categoria cat;
    public Veiculo(String placa, String marca, String cor, Categoria cat)
    {
        this.placa = placa;
        this.marca = marca;
        this.cor = cor;
        this.cat= cat;
    }
    public String getPlaca()
    {
        return placa;
    }
    public String getMarca()
    {
        return marca;
    }
    public String getCor()
    {
        return cor;
    }
    public Categoria getCategoria()
    {
        return cat;
    }
}
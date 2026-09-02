package com.labanta.servidorlocal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ServicoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private double preco;
    private Boolean estaAtivo;
    private double precoComDesconto;
    private String imagemCapa;

    public ServicoModel() {}

    public ServicoModel(
            String titulo,
            String descricao,
            double preco,
            Boolean estaAtivo,
            double precoComDesconto,
            String imagemCapa
    ) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAtivo = estaAtivo;
        this.precoComDesconto = precoComDesconto;
        this.imagemCapa = imagemCapa;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public double getPreco() {
        return this.preco;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Boolean getEstaAtivo() {
        return this.estaAtivo;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }

    public Double getPrecoComDesconto() {
        return this.precoComDesconto;
    }

    public void setPrecoComDesconto(Double precoComDesconto) {
        this.precoComDesconto = precoComDesconto;
    }

    public String getImagemCapa() {
        return this.imagemCapa;
    }

    public void setImagemCapa(String imagemCapa) {
        this.imagemCapa = imagemCapa;
    }


    public void aplicarDesconto(double percentagem) {
        double valorDesconto = (this.preco * percentagem) / 100;

        this.preco = this.preco - valorDesconto;

        System.out.println("Desconto Aplicado com sucesso!");
        System.out.println("valor final: " + this.preco);
    }

    public void verificarDisponibilidade() {
        if (this.estaAtivo) {
            System.out.println("Servico " + this.titulo + " esta disponivel!");
        } else {
            System.out.println("Servico " + this.titulo + " nao esta disponivel!");
        }
    }
}

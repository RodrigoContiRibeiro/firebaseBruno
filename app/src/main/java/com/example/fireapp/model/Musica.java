package com.example.fireapp.model;

public class Musica {
    private String nome;
    private String album;
    private String link;
    private Double avaliacao;

    public Musica(){}

    public Musica(String nome, String album, String link, Double avaliacao) {
        this.nome = nome;
        this.album = album;
        this.link = link;
        this.avaliacao = avaliacao;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
}

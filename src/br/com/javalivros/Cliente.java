package br.com.javalivros;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;

public class Cliente {
    private int id;
    private String nome;
    private LocalDate dataNascimento;
    private String email;

    public Cliente(String nome, LocalDate dataNascimento, String email) {
        Random random = new Random();

        this.id = random.nextInt(Integer.MAX_VALUE);
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome) && Objects.equals(dataNascimento, cliente.dataNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dataNascimento);
    }
}

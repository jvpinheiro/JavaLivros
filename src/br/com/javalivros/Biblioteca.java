package br.com.javalivros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    private static List<Livro> livros = new ArrayList<>();
    private static List<Autor> autores = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Emprestimo> emprestimos = new ArrayList<>();

    public Biblioteca() {
    }

    public static void main(String[] args) {
        int opcao = 0, idLivro = 0;
        String nomeLivro, nomeCliente, nomeAutor, dataNasc, email, mensagem;
        LocalDate dataNascimento = LocalDate.now();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Autor autor;
        Cliente cliente;
        Livro livro;
        Emprestimo emprestimo;
        boolean emprestimoEncontrado;

        System.out.println("Bem vindos a biblioteca do Java. O que deseja fazer?");

        while (opcao != 5) {
            System.out.println("1 - Cadastrar Livro");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Emprestar Livros");
            System.out.println("4 - Devolver Livros");
            System.out.println("5 - Sair");

            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida. Tente novamente.");
                System.out.println("");
                break;
            }

            switch (opcao) {
                case 1:
                    try {
                        System.out.println("Nome do Livro:");
                        nomeLivro = scanner.next();

                        System.out.println("Nome do Autor:");
                        nomeAutor = scanner.next();

                        System.out.println("Data de Nascimento (DD/MM/AAAA)");
                        dataNasc = scanner.next();

                        try {
                            dataNascimento = LocalDate.parse(dataNasc, formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Data de nascimento inválida. Use o formato DD/MM/AAAA.");
                            break;
                        }

                        autor = new Autor(nomeAutor, dataNascimento);
                        autor = buscarAutor(autor);
                        cadastrarAutor(autor);

                        livro = new Livro(nomeLivro, autor);
                        cadastrarLivro(livro);

                        System.out.println("Livro cadastrado com sucesso!");
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Livro inválido!");
                    }

                    break;
                case 2:
                    try {
                        System.out.println("Nome do Cliente:");
                        nomeCliente = scanner.next();

                        System.out.println("E-mail:");
                        email = scanner.next();

                        System.out.println("Data de Nascimento (DD/MM/AAAA)");
                        dataNasc = scanner.next();

                        try {
                            dataNascimento = LocalDate.parse(dataNasc, formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Data de nascimento inválida. Use o formato DD/MM/AAAA.");
                            break;
                        }

                        cliente = new Cliente(nomeCliente, dataNascimento, email);
                        cliente = buscarCliente(cliente);
                        cadastrarCliente(cliente);

                        System.out.println("Cliente cadastrado com sucesso!");
                    }
                    catch (InputMismatchException e) {
                        System.out.println("Cliente inválido!");
                    }

                    break;
                case 3:
                    System.out.println("Livros disponíveis para empréstimo:");

                    for (Livro livroCadastrado : livros) {
                        if (livroCadastrado.isDisponivel()) {
                            mensagem = String.format("Código: %d - %s - Autor: %s", livroCadastrado.getId(),
                                    livroCadastrado.getTitulo(), livroCadastrado.getAutor().getNome());
                            System.out.println(mensagem);
                        }
                    }

                    System.out.println("Qual livro será emprestado? Digite o código.");

                    try {
                        idLivro = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Livro Inválido.");
                        break;
                    }

                    livro = buscarLivroPorId(idLivro, true);

                    if (livro != null) {
                        emprestimo = new Emprestimo(livro, LocalDate.now());
                        cadastrarEmprestimo(emprestimo);
                        emprestarLivro(livro);
                    } else {
                        System.out.println("Código de livro não encontrado.");
                    }

                    break;
                case 4:
                    System.out.println("Livros emprestados:");

                    for (Livro livroCadastrado : livros) {
                        if (!livroCadastrado.isDisponivel()) {
                            mensagem = String.format("Código: %d - %s - Autor: %s", livroCadastrado.getId(),
                                    livroCadastrado.getTitulo(), livroCadastrado.getAutor().getNome());
                            System.out.println(mensagem);
                        }
                    }

                    System.out.println("Qual livro será devolvido? Digite o código.");

                    try {
                        idLivro = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Livro Inválido.");
                        break;
                    }

                    livro = buscarLivroPorId(idLivro, false);

                    if (livro != null) {
                        emprestimoEncontrado = devolverEmprestimo(livro);

                        if (!emprestimoEncontrado) {
                            System.out.println("Empréstimo não encontrado.");
                            break;
                        } else {
                            devolverLivro(livro);
                        }
                    } else {
                        System.out.println("Código de livro não encontrado.");
                    }

                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println("============================================");
            System.out.println("============================================");
        }
    }

    public static void cadastrarLivro (Livro livro) {
        livros.add(livro);
    }

    public static List<Livro> listarLivrosDisponiveis() {
        List<Livro> disponiveis = new ArrayList<>();
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                disponiveis.add(livro);
            }
        }
        return disponiveis;
    }

    public static List<Livro> listarLivros() {
        return livros;
    }

    public static Livro buscarLivroPorId (int id, boolean disponivel) {
        for (Livro livroCadastrado : livros) {
            if (livroCadastrado.getId() == id && livroCadastrado.isDisponivel() == disponivel) {
                return livroCadastrado;
            }
        }
        return null;
    }

    public static void emprestarLivro (Livro livro) {
        for (Livro livroCadastrado : livros) {
            if (livroCadastrado.getId() == livro.getId()) {
                livroCadastrado.setDisponivel(false);
                livroCadastrado.setDataAtualizacao(LocalDate.now());
            }
        }
    }

    public static void devolverLivro (Livro livro) {
        for (Livro livroCadastrado : livros) {
            if (livroCadastrado.getId() == livro.getId()) {
                livroCadastrado.setDisponivel(true);
                livroCadastrado.setDataAtualizacao(LocalDate.now());
            }
        }
    }

    public static Autor buscarAutor (Autor autor) {
        for (Autor autorCadastrado : autores) {
            if (autorCadastrado.equals(autor)) {
                return autorCadastrado;
            }
        }
        return autor;
    }

    public static void cadastrarAutor (Autor autor) {
        autores.add(autor);
    }

    public static Cliente buscarCliente (Cliente cliente) {
        for (Cliente clienteCadastrado : clientes) {
            if (clienteCadastrado.equals(cliente)) {
                return clienteCadastrado;
            }
        }

        return cliente;
    }

    public static void cadastrarCliente (Cliente cliente) {
        clientes.add(cliente);
    }

    public static void cadastrarEmprestimo (Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    public static boolean devolverEmprestimo (Livro livro) {
        for (Emprestimo emprestimoCadastrado : emprestimos) {
            if (emprestimoCadastrado.getLivro().getId() == livro.getId() &&
                    emprestimoCadastrado.getDataDevolucao() == null) {
                emprestimoCadastrado.setDataDevolucao(LocalDate.now());
                return true;
            }
        }
        return false;
    }
}

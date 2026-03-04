package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Dados;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private LivroRepository repository;
    private AutorRepository autorRepository;


    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.repository= livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivrosRegistrados(); // Adicionado aqui
                    break;
                case 3:
                    listarAutoresRegistrados(); // Adicionado aqui
                    break;
                case 4:
                    listarAutoresVivosNoAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca:");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));

        Dados dadosBusca = conversor.obterDados(json, Dados.class);

        if (dadosBusca.resultados() != null && !dadosBusca.resultados().isEmpty()) {

            DadosLivro dadosLivro = dadosBusca.resultados().get(0);

            Livro livro = new Livro(dadosLivro);

            repository.save(livro);

            System.out.println("----- LIVRO SALVO COM SUCESSO -----");
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = repository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no catálogo.");
        } else {
            System.out.println("\n---------- AUTORES ----------");
            autores.forEach(System.out::println);
            System.out.println("------------------------------\n");
        }
    }

    private void listarAutoresVivosNoAno() {
        System.out.println("Digite o ano que deseja pesquisar:");
        var ano = leitura.nextInt();
        leitura.nextLine();


        List<Autor> autoresVivos = autorRepository.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);

        if (autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado para o ano de " + ano);
        } else {
            System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
            autoresVivos.forEach(System.out::println);
            System.out.println("------------------------------\n");
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
        Digite o idioma para busca:
        es - espanhol
        en - inglês
        fr - francês
        pt - português
        """);
        var idiomaDigitado = leitura.nextLine().toLowerCase();

        Long quantidade = repository.countByIdioma(idiomaDigitado);

        if (quantidade == 0) {
            System.out.println("Nenhum livro encontrado nesse idioma no nosso banco de dados.");
        } else {
            System.out.println("\nEncontramos " + quantidade + " livro(s) no idioma: " + idiomaDigitado.toUpperCase());

            List<Livro> livrosPorIdioma = repository.findByIdioma(idiomaDigitado);

            System.out.println("---------- DETALHES ----------");
            livrosPorIdioma.forEach(System.out::println);
            System.out.println("------------------------------\n");
        }
    }
}


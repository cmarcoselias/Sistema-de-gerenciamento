import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Loja {
    static Scanner ler = new Scanner(System.in);
    static final String ARQ_PRODUTOS = "produtos.txt";
    static final String ARQ_HISTORICO = "historico.txt";

    public static void main(String[] args) {
        String opcao = "";
        
        // Garante que os arquivos existam ao iniciar o sistema
        criarArquivosSeNaoExistirem();

        System.out.println("==================\n   CLUTCH STORE  \n================== ");
        System.out.print("Seja bem-vindo! =)\n");

        do {
            System.out.println("\n-- Escolha uma opção abaixo --");
            System.out.print(
                "1 - Cadastrar produto\n" +
                "2 - Listar produtos\n" +
                "3 - Buscar produto\n" +
                "4 - Entrada de Estoque\n" +
                "5 - Saída/Venda\n" +
                "6 - Consultar estoque\n" +
                "7 - Produtos com estoque baixo\n" +
                "8 - Histórico de movimentações\n" +
                "9 - Relatório de vendas e impostos\n" +
                "0 - Sair\nOpção: "
            );
            
            try {
                opcao = ler.next();
                
                switch (opcao) {
                    case "1":
                        cadastrarProduto();
                        break;
                    case "2":
                        listarProdutos();
                        break;
                    case "3":
                        buscarProduto();
                        break;   
                    case "4":
                        entradaEstoque();
                        break;
                    case "5":
                        saidaVenda();
                        break;
                    case "6":
                        consultarEstoque();
                        break; 
                    case "7":
                        estoqueBaixo();
                        break;
                    case "8":
                        historicoMovimentacoes();
                        break;
                    case "9":
                        relatorioVendasImpostos();
                        break;
                    case "0":
                        System.out.println("Saindo do sistema. Obrigado por usar a Clutch Store!");
                        break;
                    default:
                        System.out.println("Opção inválida! Escolha entre 0 e 9.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
                ler.nextLine(); // limpar buffer
            }

        } while (!opcao.equals("0"));
    }

    public static void criarArquivosSeNaoExistirem() {
        try {
            File p = new File(ARQ_PRODUTOS);
            if (!p.exists()) p.createNewFile();
            File h = new File(ARQ_HISTORICO);
            if (!h.exists()) h.createNewFile();
        } catch (IOException e) {
            System.out.println("Erro ao inicializar arquivos de dados.");
        }
    }

    public static void registrarHistorico(String mensagem) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQ_HISTORICO, true))) {
            bw.write(mensagem);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao gravar histórico no arquivo.");
        }
    }

    public static void cadastrarProduto() {
        System.out.println("\n--- CADASTRO DE PRODUTOS ---");
        try {
            System.out.print("Informe o código do produto (ex: 001): ");
            String codigo = ler.next();
            ler.nextLine(); // limpar buffer

            System.out.print("Informe o modelo do tênis: ");
            String modelo = ler.nextLine().replace(";", "-");

            System.out.print("Informe a marca: ");
            String marca = ler.nextLine().replace(";", "-");

            int tamanho = 0;
            while (tamanho < 34 || tamanho > 44) {
                System.out.print("Informe o tamanho (catálogo de 34 ao 44): ");
                tamanho = ler.nextInt();
                if (tamanho < 34 || tamanho > 44) {
                    System.out.println("Tamanho inválido! Digite um valor entre 34 e 44.");
                }
            }

            System.out.print("Informe a cor (ex: Preto, Branco, Azul, Vermelho): ");
            String cor = ler.next();

            System.out.print("Informe o preço de venda (R$): ");
            double preco = ler.nextDouble();

            System.out.print("Informe o custo do produto (R$): ");
            double custo = ler.nextDouble();

            int quantidade = 0;
            while (quantidade <= 0 || quantidade > 1000) {
                System.out.print("Informe a quantidade inicial (1 a 1000): ");
                quantidade = ler.nextInt();
                if (quantidade <= 0 || quantidade > 1000) {
                    System.out.println("Quantidade inválida! Deve ser entre 1 e 1000.");
                }
            }

            // Salvando no arquivo com BufferedWriter em modo append (true)
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQ_PRODUTOS, true))) {
                // Formato: codigo;modelo;marca;tamanho;cor;preco;custo;quantidade
                String linha = codigo + ";" + modelo + ";" + marca + ";" + tamanho + ";" + cor + ";" + preco + ";" + custo + ";" + quantidade;
                bw.write(linha);
                bw.newLine();
                System.out.println("Produto cadastrado e salvo no arquivo com sucesso!");
                registrarHistorico("Cadastro do produto: " + modelo + " (" + cor + ", Tam " + tamanho + "), Qtd: " + quantidade);
            }

        } catch (InputMismatchException e) {
            System.out.println("Erro: Entrada de dados inválida. Certifique-se de digitar números onde for solicitado.");
            ler.nextLine(); // limpar buffer
        } catch (IOException e) {
            System.out.println("Erro de E/S ao salvar o produto.");
        }
    }

    public static void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQ_PRODUTOS));
            if (linhas.isEmpty()) {
                System.out.println("Nenhum produto cadastrado no arquivo.");
                return;
            }
            System.out.println("Código | Modelo | Marca | Tam | Cor | Preço | Estoque");
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length == 8) {
                    System.out.println(partes[0] + " | " + partes[1] + " | " + partes[2] + " | " + partes[3] + " | " + partes[4] + " | R$" + partes[5] + " | " + partes[7]);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de produtos.");
        }
    }

    public static void buscarProduto() {
        System.out.println("\n--- BUSCAR PRODUTO ---");
        System.out.print("Digite o código ou modelo do produto: ");
        String busca = ler.next();
        boolean encontrado = false;

        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQ_PRODUTOS));
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length == 8 && (partes[0].equalsIgnoreCase(busca) || partes[1].equalsIgnoreCase(busca))) {
                    System.out.println("Encontrado -> Código: " + partes[0] + " | Modelo: " + partes[1] + " | Marca: " + partes[2] + " | Tam: " + partes[3] + " | Cor: " + partes[4] + " | Preço: R$" + partes[5] + " | Estoque: " + partes[7]);
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de produtos.");
        }

        if (!encontrado) {
            System.out.println("Produto não encontrado.");
        }
    }

    public static void entradaEstoque() {
        System.out.println("\n--- ENTRADA DE ESTOQUE ---");
        System.out.print("Digite o código do produto: ");
        String codigo = ler.next();
        boolean encontrado = false;

        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQ_PRODUTOS));
            for (int i = 0; i < linhas.size(); i++) {
                String[] partes = linhas.get(i).split(";");
                if (partes.length == 8 && partes[0].equalsIgnoreCase(codigo)) {
                    System.out.print("Quantidade a adicionar: ");
                    int qtdAdicionar = ler.nextInt();
                    if (qtdAdicionar > 0) {
                        int qtdAtual = Integer.parseInt(partes[7]);
                        int novaQtd = qtdAtual + qtdAdicionar;
                        partes[7] = String.valueOf(novaQtd);

                        // Reconstrói a linha e atualiza na lista
                        linhas.set(i, String.join(";", partes));
                        
                        // Reescreve o arquivo completo com o estoque atualizado
                        reescreverArquivoProdutos(linhas);

                        System.out.println("Estoque atualizado com sucesso! Novo estoque: " + novaQtd);
                        registrarHistorico("Entrada de " + qtdAdicionar + " unidades no produto: " + partes[1]);
                    } else {
                        System.out.println("Quantidade deve ser maior que zero.");
                    }
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException | InputMismatchException e) {
            System.out.println("Erro ao processar entrada de estoque.");
            ler.nextLine();
        }

        if (!encontrado) {
            System.out.println("Produto não encontrado.");
        }
    }

    public static void saidaVenda() {
        System.out.println("\n--- SAÍDA / VENDA DE PRODUTO ---");
        System.out.print("Digite o código do produto vendido: ");
        String codigo = ler.next();
        boolean encontrado = false;

        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQ_PRODUTOS));
            for (int i = 0; i < linhas.size(); i++) {
                String[] partes = linhas.get(i).split(";");
                if (partes.length == 8 && partes[0].equalsIgnoreCase(codigo)) {
                    System.out.print("Quantidade vendida: ");
                    int qtdVenda = ler.nextInt();
                    int qtdAtual = Integer.parseInt(partes[7]);

                    if (qtdVenda > 0 && qtdVenda <= qtdAtual) {
                        int novaQtd = qtdAtual - qtdVenda;
                        partes[7] = String.valueOf(novaQtd);
                        linhas.set(i, String.join(";", partes));

                        reescreverArquivoProdutos(linhas);

                        double preco = Double.parseDouble(partes[5]);
                        double valorTotalVenda = preco * qtdVenda;
                        
                        // Cálculos tributários da Clutch Store
                        double icms = valorTotalVenda * 0.18;
                        double pis = valorTotalVenda * 0.0065;
                        double cofins = valorTotalVenda * 0.03;
                        double irpj = valorTotalVenda * 0.048;
                        double csll = valorTotalVenda * 0.0288;
                        double totalImpostos = icms + pis + cofins + irpj + csll;

                        System.out.println("\nVenda realizada com sucesso!");
                        System.out.printf("Valor total da venda: R$ %.2f\n", valorTotalVenda);
                        System.out.printf("Total de impostos incidentes: R$ %.2f\n", totalImpostos);
                        
                        registrarHistorico("Venda de " + qtdVenda + " unidades do produto: " + partes[1]);
                    } else {
                        System.out.println("Quantidade inválida ou estoque insuficiente.");
                    }
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException | InputMismatchException e) {
            System.out.println("Erro ao processar a venda.");
            ler.nextLine();
        }

        if (!encontrado) {
            System.out.println("Produto não encontrado.");
        }
    }

    public static void consultarEstoque() {
        listarProdutos();
    }

    public static void estoqueBaixo() {
        System.out.println("\n--- PRODUTOS COM ESTOQUE BAIXO (<= 5) ---");
        boolean temBaixo = false;
        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQ_PRODUTOS));
            for (String linha : linhas) {
                String[] partes = linha.split(";");
                if (partes.length == 8) {
                    int qtd = Integer.parseInt(partes[7]);
                    if (qtd <= 5) {
                        System.out.println("ALERTA: " + partes[1] + " (Tam: " + partes[3] + ", Cor: " + partes[4] + ") - Estoque: " + qtd);
                        temBaixo = true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler dados do arquivo.");
        }

        if (!temBaixo) {
            System.out.println("Nenhum produto com estoque baixo no momento.");
        }
    }

    public static void historicoMovimentacoes() {
        System.out.println("\n--- HISTÓRICO DE MOVIMENTAÇÕES ---");
        try {
            List<String> linhas = Files.readAllLines(Paths.get(ARQ_HISTORICO));
            if (linhas.isEmpty()) {
                System.out.println("Nenhuma movimentação registrada.");
                return;
            }
            for (String linha : linhas) {
                System.out.println("- " + linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o histórico.");
        }
    }

    public static void relatorioVendasImpostos() {
        System.out.println("\n--- RELATÓRIO DE TRIBUTOS DA CLUTCH STORE ---");
        System.out.println("Alíquotas configuradas no sistema:");
        System.out.println("- ICMS: 18%");
        System.out.println("- PIS: 0,65%");
        System.out.println("- COFINS: 3%");
        System.out.println("- IRPJ: 4,8%");
        System.out.println("- CSLL: 2,88%");
        System.out.println("\nTodas as operações de saída calculam automaticamente esses tributos com base no valor total[cite: 25, 26].");
    }

    public static void reescreverArquivoProdutos(List<String> linhas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQ_PRODUTOS, false))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo de produtos.");
        }
    }
}
import java.util.Scanner;

public class Loja {
    static Scanner ler = new Scanner(System.in);
    public static void main(String[] args) {
        //Oq vai ter nesta bonba
        // Cadastro e alteração de produtos.
        //Listagem e busca.
        //Controle de entradas e saídas.
        //Registro de vendas.
        //Consulta de estoque.
        //Alertas de estoque baixo.
        //Histórico de movimentações.
        //Relatórios de vendas e impostos.
        System.out.println("============\nCLUTCH STORE\n============ ");
        System.out.print("seja bem-vindo! =)\n");
        System.out.println("oque deseja fazer?!");

        // professor quer  tratamento e exessões(try e cash)

        int quantidade = 0;
        System.out.println("==================\n   CLUTCH STORE  \n================== ");
        System.out.print("seja bem-vindo!\n");
        System.out.println("\noque deseja fazer?");
        String nome;
        String opcao;
        do{
            System.out.println("\n--Escolha uma opção abaixo--\n");
            System.out.print(
                "1-Cadastrar produto\n2-Listar produtos\n3-Buscar\n4-Entrada de Estoque\n5-Saída/Venda"+
                "\n6-Consultar estoque\n7-Produtos com estoque baixo\n8-Histórico de movimentações"+
                "\n9-Relatório de vendas\n0-Sair \nopção: "
            );
            opcao = ler.next();
            switch (opcao) {
                //cadastro -> Marcos
                case "1":
                    System.out.println("\n---CADASTRO DE PRODUTOS---");
                    
                    // limite de cadastros entre 1 e 1000
                    while (quantidade == 0 || quantidade > 1000) {
                    System.out.println("informe a qunatidade que será cadastrada ");
                     quantidade = ler.nextInt();
                    if (quantidade == 0 || quantidade > 1000) {
                    System.out.println("quantidade inválida");
                    continue;
                    }
                 }
                    break;
                case"2":
                    break;
                case"3":
                    break;   
                case"4":
                    break;
                case"5":
                    break;
                case"6":
                    break; 
                case"7":
                    break;
                case"8":
                    break;
                case"9":
                    break;
                default:
                    break;
            }


        }while(!opcao.equals("0"));


    }

    
}

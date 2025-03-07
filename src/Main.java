import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //instanciando a classe ArvoreDeArquivo
        ArvoreDeArquivo arvore = new ArvoreDeArquivo();

        //Quero que teste dos dois jeitos, sor! Obrigado

        // Exemplo de criação sem menu
//        arvore.mkdir("/gian");
//        arvore.mkdir("/gian/home");
//        arvore.mkdir("/gian/home/bin");
//        arvore.ls("/gian/home");
//        System.out.println(arvore.toDot());


        Scanner teclado = new Scanner(System.in); //criação do Scanner que recebe as entradas via teclado
        int opcao; //variável que armazena a opção escolhida pelo usuário

        do {
            //menu de opções para o usuário escolher o que deseja fazer
            System.out.println("============ MENU =============");
            System.out.println("1. Criar diretório (mkdir)");
            System.out.println("2. Listar o conteúdo de um diretório (ls)");
            System.out.println("3. Remover diretório/arquivo (rm)");
            System.out.println("4. Exit");
            System.out.println("Escolha uma opção:");

          //lê a escolha o usuário
            opcao = teclado.nextInt();
            teclado.nextLine(); //quebra de linha

          //switch case que realiza ações com base na opção escolhida
            switch (opcao) {
                case 1: //opção para criar diretório
                    System.out.println("Informe o caminho do diretório a ser criado:");
                    String criarDiretorio = teclado.nextLine();
                    try {
                        //chama o método mkdir
                        if (arvore.mkdir(criarDiretorio)) {
                            System.out.println("Diretório criado!");
                        } else {
                            System.out.println("Não foi possível criar o diretório.");
                            //se não for possível
                        }
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                        //trata as exceções do método mkdir
                    }
                    break;

                case 2: //opção para listar o conteúdo de um diretório
                    System.out.println("Digite o caminho do diretório que deseja listar:");
                    String listarDiretorio = teclado.nextLine();//lê o dado fornecido pelo usuário
                    try {
                        //chama o método ls da classe ArvoreDeArquivo
                        arvore.ls(listarDiretorio);
                    } catch (Exception e) {
                        //trata as exceções do método ls
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3: //remover um diretório ou arquivo
                    System.out.println("Digite o caminho do diretório ou arquivo que deseja remover:");
                    String removerCaminho = teclado.nextLine();//lê o dado fornecido pelo usuário
                    try {
                        //chama o método rm da classe ArvoreDeArquivo
                        if (arvore.rm(removerCaminho)) {
                            System.out.println("Removido com sucesso.");
                        } else {
                            System.out.println("Não foi possível remover.");//se não for possivel
                        }
                    } catch (Exception e) {
                        //trata exceções lançadas pelo método rm
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 4://opcao para sair do programa
                    arvore.exit(); //chama o método exit
                    break;

                default: //caso o usuário escolha uma opção inválida
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 4);
        //continua exibindo o menu até que o usuário escolha a opção de saída

    }
}

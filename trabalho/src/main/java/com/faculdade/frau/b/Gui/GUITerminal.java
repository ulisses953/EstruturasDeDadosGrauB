package com.faculdade.frau.b.Gui;

import java.util.Scanner;

import com.faculdade.frau.b.service.ServiceUser;

public class GUITerminal {
    ServiceUser serviceUser = new ServiceUser();
  
    public void start() {
        serviceUser.getUserByFileCSV("trabalho/src/main/resources/nomes.csv", ';', true);
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

          while (opcao != 8) {
            System.out.println("======================================");
            System.out.println("     Estruturas - Trabalho Grau B     ");
            System.out.println("======================================");
            System.out.println("1. Pesquisa com cpf");
            System.out.println("2. Regras e Orientações");
            System.out.println("3. Exemplos de Entrada e Saída");
            System.out.println("4. Descrição das Estruturas");
            System.out.println("5. Critérios de Avaliação");
            System.out.println("6. Dúvidas Frequentes");
            System.out.println("7. Enviar Trabalho");
            System.out.println("8. Sair");
            System.out.print("Selecione uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o CPF para pesquisa: ");
                    String cpf = scanner.nextLine();
                    
                    if (cpf.isEmpty()) {
                        System.out.println("CPF não pode ser vazio.");
                    } else {
                        var user = serviceUser.getUserByCPF(Long.parseLong(cpf));
                        if (user != null) {
                            System.out.println("Usuário encontrado: " + user.toString());
                        } else {
                            System.out.println("Usuário não encontrado.");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Regras e Orientações: ...");
                    break;
                case 3:
                    System.out.println("Exemplos de Entrada e Saída: ...");
                    break;
                case 4:
                    System.out.println("Descrição das Estruturas: ...");
                    break;
                case 5:
                    System.out.println("Critérios de Avaliação: ...");
                    break;
                case 6:
                    System.out.println("Dúvidas Frequentes: ...");
                    break;
                case 7:
                    System.out.println("Enviar Trabalho: ...");
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }
        scanner.close();
    }


}
    




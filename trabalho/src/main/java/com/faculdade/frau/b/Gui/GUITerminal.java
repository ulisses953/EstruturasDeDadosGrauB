package com.faculdade.frau.b.Gui;

import java.util.Calendar;
import java.util.Scanner;

import com.faculdade.frau.b.service.ServiceUser;

public class GUITerminal {
    ServiceUser serviceUser = new ServiceUser();

    public void start() {
        serviceUser.getUserByFileCSV("trabalho/src/main/resources/nomes.csv", ';', true);
        serviceUser.insertDateAvl();
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 8) {
            System.out.println("======================================");
            System.out.println("     Estruturas - Trabalho Grau B     ");
            System.out.println("======================================");
            System.out.println("1. Pesquisar por CPF");
            System.out.println("2. Pesquisar por Data de Nascimento");
            System.out.println("3. Pesquisar por Nome");
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
                    System.out.println("Digite a data de nascimento para pesquisa (formato: dd/MM/yyyy): ");
                    String dataNascimento = scanner.nextLine();

                    if (dataNascimento.isEmpty()) {
                        System.out.println("Data de nascimento não pode ser vazia.");
                    }

                    System.out.println("Digite a data de nascimento para pesquisa (formato: dd/MM/yyyy): ");
                    String dataNascimento2 = scanner.nextLine();

                    if (dataNascimento2.isEmpty()) {
                        System.out.println("Data de nascimento não pode ser vazia.");
                    }

                    Calendar data = stringToCalendar(dataNascimento);
                    Calendar data2 = stringToCalendar(dataNascimento2);

                    if ((data != null) || (data2 != null)) {
                        serviceUser.getUserByDate(data, data2).forEach(user -> {
                            System.out.println("Usuário encontrado: " + user.toString());
                        });
                    }

                    break;
                case 3:
                    System.out.println("Digite o nome para pesquisa: ");
                    String name = scanner.nextLine();
                    if (name.isEmpty()) {
                        System.out.println("Nome não pode ser vazio.");
                    } else {
                        var users = serviceUser.getUserByName(name);
                        if (users != null && !users.isEmpty()) {
                            System.out.println("Usuários encontrados: ");
                            for (var user : users) {
                                System.out.println(user.toString());
                            }
                        } else {
                            System.out.println("Nenhum usuário encontrado com esse nome.");
                        }
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }
        scanner.close();
    }

    public Calendar stringToCalendar(String dataStr) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = sdf.parse(dataStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (java.text.ParseException e) {
            return null; // ou lance uma exceção, se preferir
        }
    }
}
package com.faculdade.frau.b.Gui;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.faculdade.frau.b.DTO.DtoUserHeight;
import com.faculdade.frau.b.service.ServiceUser;

public class GUITerminal {
    ServiceUser serviceUser = new ServiceUser();

    public void start() {
        serviceUser.getUserByFileCSV("trabalho/src/main/resources/nomes.csv", ';', true);
        serviceUser.insertDateAvl();
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 4) {
            System.out.println("======================================");
            System.out.println("     Estruturas - Trabalho Grau B     ");
            System.out.println("======================================");
            System.out.println("1. Pesquisar por CPF");
            System.out.println("2. Pesquisar por Data de Nascimento");
            System.out.println("3. Pesquisar por Nome");
            System.out.println("4. Sair");
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
                        break;
                    }

                    var resultCPF = serviceUser.getUserByCPF(Long.parseLong(cpf));

                    if (resultCPF == null) {
                        System.out.println("Usuário não encontrado.");
                        break;
                    }

                    System.out.println("Usuário: " + resultCPF.user().toString());
                    System.out.println("Altura do node: " + resultCPF.height());

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

                    if ((data == null) || (data2 == null)) {
                        break;
                    }

                    List<DtoUserHeight> resultDate = serviceUser.getUserByDate(data, data2);

                    if (resultDate.isEmpty()) {
                        System.out.println("Nenhum usuário encontrado com essa data de nascimento.");
                        break;
                    }

                    System.out.println("Total de usuários encontrados: " + resultDate.size());

                    resultDate.forEach(dto -> {
                        System.out.println("Usuário: " + dto.user().toString());
                        System.out.println("Altura do node: " + dto.height());
                    });

                    break;
                case 3:
                    System.out.println("Digite o nome para pesquisa: ");
                    String name = scanner.nextLine();

                    if (name.isEmpty()) {
                        System.out.println("Nome não pode ser vazio.");
                        break;
                    }

                    List<DtoUserHeight> resultName = serviceUser.getUserByName(name);

                    if (resultName == null && resultName.isEmpty()) {
                        System.out.println("Nenhum usuário encontrado com esse nome.");
                        break;
                    }

                    System.out.println("Usuários encontrados: ");

                    System.out.println("Total de usuários encontrados: " + resultName.size());

                    resultName.forEach(dto -> {
                        System.out.println("Usuário: " + dto.user().toString());
                        System.out.println("Altura do node: " + dto.height());
                    });

                    break;

                case 4:
                    System.out.println("Saindo do programa...");
                    opcao = 4; // Sair do loop
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
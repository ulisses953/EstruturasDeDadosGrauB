package com.faculdade.frau.b.service;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faculdade.frau.b.Model.User;
import com.faculdade.frau.b.Model.Avl.Node;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class ServiceUser {
    private ArrayList<User> users = new ArrayList<>();

    private ServiceAVL<Long> serviceAVLCPF = new ServiceAVL<>();
    private ServiceAVL<Calendar> serviceAVLData = new ServiceAVL<>();
    private ServiceAVL<String> serviceAVLName = new ServiceAVL<>();

    private static final Logger logger = LoggerFactory.getLogger(ServiceUser.class);

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public int getUserByFileCSV(String file, char separator, boolean hasHeader) {

        if (file == null || file.isEmpty()) {
            logger.error("O caminho do arquivo CSV não pode ser nulo ou vazio.");
            return 0;
        }
        if (separator == '\0') {
            separator = ',';
        }
        if (!file.endsWith(".csv")) {
            logger.error("O arquivo deve ser um arquivo CSV.");
            return 0;
        }
        if (!new java.io.File(file).exists()) {
            logger.error("O arquivo CSV não existe: " + file);
            return 0;
        }
        users.clear();

        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                    .withCSVParser(new CSVParserBuilder().withSeparator(separator).build()).build();

            if (hasHeader) {
                reader.readNext();
            }

            String[] line;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // ajuste o formato conforme seu CSV
            while ((line = reader.readNext()) != null) {
                User user = new User();

                user.setCpf(Long.parseLong(line[0]));
                user.setRG(Long.parseLong(line[1]));
                user.setName(line[2]);

                // Conversão correta da data
                Calendar cal = Calendar.getInstance();
                Date date = sdf.parse(line[3]);
                cal.setTime(date);
                user.setDateOfBirth(cal);

                user.setCity(line[4]);

                logger.info("Usuário lido do CSV: " + user);
                users.add(user);
            }
            reader.close();
        } catch (IOException | CsvException | ParseException e) {
            logger.error("Erro ao ler o arquivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            logger.error("Erro ao converter dados do CSV: " + e.getMessage());
        }

        logger.info("Total de usuários lidos: " + users.size());
        return users.size();
    }

    public void insertDateAvl() {
        logger.info("Inserindo usuários no AVL por CPF");
        insertAvlCPF();
        logger.info("Inserindo usuários no AVL por Data de Nascimento");
        InsertAvlData();
    }

    public void insertAvlCPF() {
        for (int i = 0; i < users.size(); i++) {
            serviceAVLCPF.insert(users.get(i).getCpf(), i);
        }
    }

    public void InsertAvlData() {
        for (int i = 0; i < users.size(); i++) {
            Calendar data = users.get(i).getDateOfBirth();
            zerarHora(data);
            serviceAVLData.insert(data, i);
        }
    }

    public void InsertAvlName() {
        for (int i = 0; i < users.size(); i++) {
            serviceAVLName.insert(users.get(i).getName(), i);
        }
    }

    public User getUserByCPF(Long cpf) {
        if (cpf == null || cpf <= 0) {
            logger.error("O CPF não pode ser nulo ou menor ou igual a zero.");
            return null;
        }
        try {
            Node<Long> node = serviceAVLCPF.findNode(cpf);

            if (node != null) {
                int index = node.getPointer().get(0);

                logger.info("Usuário encontrado com CPF: " + cpf);
                logger.info("Usuário: " + users.get(index).toString());

                return users.get(index);
            } else {
                logger.info("Usuário não encontrado com CPF: " + cpf);
                return null;
            }

        } catch (NumberFormatException e) {
            logger.error("CPF inválido: " + cpf);
            return null;
        }

    }

    public List<User> getUserByDate(Calendar date1, Calendar date2) {
        zerarHora(date1);
        zerarHora(date2);

        List<Calendar> dates = getAllDatesBetween(date1, date2);
        List<User> usersFound = new ArrayList<>();
        if (dates.isEmpty()) {
            logger.info("Nenhuma data válida encontrada entre as datas fornecidas.");
            return usersFound;
        }
        for (Calendar date : dates) {
            Node<Calendar> node = serviceAVLData.findNode(date);
            if (node != null) {
                for (Integer index : node.getPointer()) {
                    usersFound.add(users.get(index));
                }
            }
        }

        if (usersFound.isEmpty()) {
            logger.info("Nenhum usuário encontrado entre as datas fornecidas.");
        } else {
            logger.info("Usuários encontrados entre as datas fornecidas: " + usersFound.size());
        }

        for (User user : usersFound) {
            logger.info("Usuário encontrado: " + user.toString());
        }

        return usersFound;
    }

    public List<Calendar> getAllDatesBetween(Calendar date1, Calendar date2) {
        List<Calendar> dates = new ArrayList<>();
        if (date1 == null || date2 == null || date1.after(date2)) {
            return dates;
        }

        Calendar current = (Calendar) date1.clone();

        while (!current.after(date2)) {
            dates.add((Calendar) current.clone());
            current.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    public List<User> getUserByName(String name) {
        if (name == null || name.isEmpty()) {
            logger.error("O nome não pode ser nulo ou vazio.");
            return new ArrayList<>();
        }

        List<User> usersFound = new ArrayList<>();
        collectUsersByNamePrefix(serviceAVLName.getRoot(), name.toLowerCase(), usersFound);

        if (usersFound.isEmpty()) {
            logger.info("Nenhum usuário encontrado com o nome: " + name);
        } else {
            logger.info("Usuários encontrados com o nome: " + name);
        }

        return usersFound;
    }

    private void collectUsersByNamePrefix(Node<String> node, String prefix, List<User> usersFound) {
        if (node == null)
            return;

        String nodeName = node.getKey();
        System.out.println("Verificando nó: " + nodeName);
        if (nodeName != null && nodeName.toLowerCase().startsWith(prefix)) {
            for (Integer index : node.getPointer()) {
                usersFound.add(users.get(index));
            }
        }

        collectUsersByNamePrefix(node.getLeft(), prefix, usersFound);
        collectUsersByNamePrefix(node.getRight(), prefix, usersFound);
    }

    public void zerarHora(Calendar cal) {
    if (cal != null) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
}
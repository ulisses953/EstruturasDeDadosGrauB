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


    private static final Logger logger = LoggerFactory.getLogger(ServiceUser.class);


    /**
     * Reads users from a CSV file and adds them to the user list.
     *
     * <p>The CSV file must contain the following fields in each row:
     * <ul>
     *   <li>CPF (long)</li>
     *   <li>RG (long)</li>
     *   <li>Name (String)</li>
     *   <li>Date of birth (String in the format "dd/MM/yyyy")</li>
     *   <li>City (String)</li>
     * </ul>
     *
     * @param file       Path to the CSV file.
     * @param separator  Field separator in the CSV file. If '\0', ',' will be used.
     * @param hasHeader  Indicates if the CSV file has a header row.
     * @return           The number of users read and added to the list.
     *
     * @throws IllegalArgumentException If the file path is null or empty,
     *                                  if the file is not a CSV, or does not exist.
     * @throws IOException             If an error occurs while reading the file.
     * @throws CsvException            If an error occurs while processing the CSV.
     * @throws ParseException          If an error occurs while parsing the date of birth.
     * @throws NumberFormatException   If an error occurs while parsing CPF or RG.
     */
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

        insertDateAvl();

        logger.info("Total de usuários lidos: " + users.size());
        return users.size();
    }

    public void insertDateAvl(){
        insertAvlCPF();
    }

    protected void insertAvlCPF() {
        for (int i = 0; i < users.size(); i++) {
            serviceAVLCPF.insert(users.get(i).getCpf(), i);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
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

    
    

}

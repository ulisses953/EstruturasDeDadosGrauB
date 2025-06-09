package com.faculdade.frau.b.service;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faculdade.frau.b.Model.User;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class ServiceUser {
    private ArrayList<User> users = new ArrayList<>();

    public ServiceAVL<Long> serviceAVLCPF = new ServiceAVL<>();
    public ServiceAVL<Long> serviceAVLRG = new ServiceAVL<>();
    public ServiceAVL<Calendar> serviceAVLData = new ServiceAVL<>();


    private static final Logger logger = LoggerFactory.getLogger(ServiceUser.class);

    /**
     * Obtém varios usuário a partir de um arquivo CSV
     * @param file caminho para o arquivo csv
     * @return int quantidade de usuários obtidos
     * @author Ulisses
     * @since 1.0
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

        logger.info("Total de usuários lidos: " + users.size());
        return users.size();
    }

    public void insertAvlCPF() {
        for (int i = 0; i < users.size(); i++) {
            serviceAVLCPF.insert(users.get(i).getCpf(), i);
        }
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    
    

}

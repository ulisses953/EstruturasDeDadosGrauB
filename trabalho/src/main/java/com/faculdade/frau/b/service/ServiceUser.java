package com.faculdade.frau.b.service;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faculdade.frau.b.Model.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ServiceUser {
    private ArrayList<User> users = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ServiceUser.class);

    /**
     * Obtém varios usuário a partir de um arquivo CSV
     * @param file caminho para o arquivo csv
     * @return int quantidade de usuários obtidos
     * @author Ulisses
     * @since 1.0
     */
    public int getUserByFileCSV(String file){
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] line;
            while ((line = reader.readNext()) != null) {
                User user = new User();
                user.setCpf(Long.parseLong(line[0]));
                user.setRG(Long.parseLong(line[1]));
                user.setName(line[2]);
                user.setDateOfBirth(LocalDate.parse(line[3]));
                user.setCity(line[4]);

                // Adiciona o usuário à lista
                logger.info("Usuário lido do CSV: " + user);
                users.add(user);
            }
            reader.close();
        } catch (IOException | CsvException e) {
            logger.error("Erro ao ler o arquivo CSV: " + e.getMessage());
        }catch (NumberFormatException e) {
            logger.error("Erro ao converter dados do CSV: " + e.getMessage());
        }

        return users.size();
    }
    
    public ArrayList<User> getUsers() {
        return users;
    }

    
    

}

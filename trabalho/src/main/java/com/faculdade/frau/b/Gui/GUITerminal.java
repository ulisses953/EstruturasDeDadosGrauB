package com.faculdade.frau.b.Gui;

import com.faculdade.frau.b.service.ServiceUser;

public class GUITerminal {
    ServiceUser serviceUser = new ServiceUser();
  
    public void start() {
        System.out.println("\n \n Bem-vindo ao sistema de gerenciamento de usuários!");
        System.out.println("Carregando usuários do arquivo CSV...");
        serviceUser.getUserByFileCSV("trabalho/src/main/resources/nomes.csv", ';', true);
    }
    


}

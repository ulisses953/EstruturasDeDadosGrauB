package com.faculdade.frau.b.Model;

import java.util.Calendar;

public class User {
    private Long cpf;
    private Long RG;
    private String name;
    private Calendar dateOfBirth;
    private String city;

    public User(Long cpf, Long RG, String name, Calendar dateOfBirth, String city) {
        this.cpf = cpf;
        this.RG = RG;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
    }

    public User() {
    }
    
    public Long getCpf() {
        return cpf;
    }
    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }
    public Long getRG() {
        return RG;
    }
    public void setRG(Long RG) {
        this.RG = RG;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        String dateStr = "";
        if (dateOfBirth != null) {
            int day = dateOfBirth.get(Calendar.DAY_OF_MONTH);
            int month = dateOfBirth.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
            int year = dateOfBirth.get(Calendar.YEAR);
            dateStr = String.format("%02d/%02d/%04d", day, month, year);
        }
        return "User{" +
                "cpf=" + cpf +
                ", RG=" + RG +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateStr +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!cpf.equals(user.cpf)) return false;
        if (!RG.equals(user.RG)) return false;
        if (!name.equals(user.name)) return false;
        if (!dateOfBirth.equals(user.dateOfBirth)) return false;
        return city.equals(user.city);
    }

}

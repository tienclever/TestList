package com.example.testlist;

public class SinhVien {
    String Name;
    String dateOfbirth;
    String phoneNumber;
    String specialized;
    String Level;

    public SinhVien(String name, String dateOfbirth, String phoneNumber, String specialized, String level) {
        Name = name;
        this.dateOfbirth = dateOfbirth;
        this.phoneNumber = phoneNumber;
        this.specialized = specialized;
        Level = level;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDateOfbirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(String dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }
}

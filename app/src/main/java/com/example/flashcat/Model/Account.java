package com.example.flashcat.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    private String ID_Account;
    private String username;
    private String first_name;
    private String last_name;
    private String password;
    private String image;
    private String email;

    public Account() {
    }

    public Account(String ID_Account, String username, String first_name, String last_name, String password, String image, String email) {
        this.ID_Account = ID_Account;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.image = image;
        this.email = email;
    }

    public String getID_Account() {
        return ID_Account;
    }

    public void setID_Account(String ID_Account) {
        this.ID_Account = ID_Account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //validate sign in
    public boolean validate_username(String username){
        String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,16}$";
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
    public boolean validate_first_name(String first_name){
        String FIRSTNAME_PATTERN = "^[a-zA-Z]{2,16}$";
        Pattern pattern = Pattern.compile(FIRSTNAME_PATTERN);
        Matcher matcher = pattern.matcher(first_name);
        return matcher.matches();
    }
    public boolean validate_last_name(String last_name){
        String LASTNAME_PATTERN = "^[a-zA-Z]{2,16}$";
        Pattern pattern = Pattern.compile(LASTNAME_PATTERN);
        Matcher matcher = pattern.matcher(last_name);
        return matcher.matches();
    }
    public boolean validate_password(String password){
        String PASSWORD_PATTERN = "^\\d{8,50}";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public Account(String username, String first_name, String last_name, String password, String email) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.email = email;
    }
    public Account(String first_name,String last_name,String username,String image){
        this.first_name = first_name;
        this.last_name = last_name;
        this.username =username;
        this.image = image;
    }
}

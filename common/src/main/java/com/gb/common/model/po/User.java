package com.gb.common.model.po;

/**
 * @author gblau
 * @date 2016-11-12
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String message;

    public User() { }

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public boolean isUsernameEmpty() {
        return this.username == null || "".equals(this.username);
    }

    public boolean isPasswordEmpty() {
        return this.password == null || "".equals(this.password);
    }

    public boolean isEmailEmpty() {
        return this.email == null || "".equals(this.email);
    }

    public boolean isPhoneEmpty() {
        return this.phone == null || "".equals(this.password);
    }

    public boolean isMessageEmpty() {
        return this.message == null || "".equals(this.message);
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String name) {
        this.username = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public User setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;
        return password.equals(user.password);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}

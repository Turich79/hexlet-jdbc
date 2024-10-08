package io.hexlet;

public class User {
    private Long id;
    private String username;
    private String phone;

    public User(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

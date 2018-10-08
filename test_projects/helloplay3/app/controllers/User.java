package controllers;

import play.mvc.PathBindable;

import java.util.Optional;

public class User implements PathBindable<User> {


    private Integer userId;
    private String username;
    private String email;
    private String password;
    private Integer age;


    @Override
    public String toString() {
        return username;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public User bind(String key, String id) {
        // findById meant to be lightweight operation
        Optional<User> user = UserController.findUser(new Integer(id));
        if (user == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        return user.get();
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(userId);
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}

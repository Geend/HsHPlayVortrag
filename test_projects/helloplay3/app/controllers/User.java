package controllers;

import play.mvc.PathBindable;

import java.util.Optional;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import validation.CreateUserCheck;
import validation.EditUserCheck;
import validation.LoginUserCheck;

import java.util.ArrayList;
import java.util.List;

@Constraints.Validate(groups = {CreateUserCheck.class, EditUserCheck.class})
public class User implements Constraints.Validatable<List<ValidationError>>, PathBindable<controllers.User> {


    private Integer userId;

    @Constraints.Required(groups = {CreateUserCheck.class, LoginUserCheck.class})
    @Constraints.MaxLength(20)
    private String username;

    @Constraints.Email
    private List<String> emails;

    @Constraints.Required(groups = {CreateUserCheck.class, LoginUserCheck.class})
    @Constraints.MinLength(3)
    private String password;


    @Constraints.Required(groups = {CreateUserCheck.class})
    @Constraints.Min(value = 18, groups = {CreateUserCheck.class, EditUserCheck.class})
    @Constraints.Max(value = 99, groups = {CreateUserCheck.class, EditUserCheck.class})
    private Integer age;

    private Boolean admin;

    @Override
    public String toString() {
        return username + " " + admin;
    }


    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (username.equals("admin") && !admin)
            errors.add(new ValidationError("username", "username darf nicht admin sein"));

        if (password.equals(username))
            errors.add(new ValidationError("password", "passwort muss != username sein"));

        return errors;
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


    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
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

}

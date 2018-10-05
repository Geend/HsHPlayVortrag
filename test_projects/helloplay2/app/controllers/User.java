package controllers;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import validation.CreateUserCheck;

@Constraints.Validate
public class User implements Constraints.Validatable<ValidationError> {


    private Integer userId;

    @Constraints.Required
    @Constraints.MinLength(3)
    @Constraints.MaxLength(15)
    @Constraints.Pattern("([a-z])\\w+")
    private String username;

    @Constraints.Email
    private String email;

    @Constraints.Required(groups = CreateUserCheck.class)
    @Constraints.MinLength(4)
    private String password;

    @Constraints.Required
    @Constraints.Min(18)
    @Constraints.Max(99)
    private Integer age;


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
    public String toString() {
        return username;
    }

    @Override
    public ValidationError validate() {
        if(username.equals("admin")){
            return new ValidationError("username", "Benutzername \"admin\" ist unzulässig!");
        }
        return null;
    }
}

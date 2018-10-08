package controllers;

import play.Logger;
import play.mvc.PathBindable;

import java.util.Map;
import java.util.Optional;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.mvc.QueryStringBindable;
import scala.Function1;
import scala.Option;
import scala.collection.Seq;
import scala.util.Either;

import java.util.ArrayList;
import java.util.List;

@Constraints.Validate
public class User implements Constraints.Validatable<List<ValidationError>>, QueryStringBindable<User> {

    private Integer userId;

    @Constraints.Required
    @Constraints.MaxLength(20)
    public String username;

    @Constraints.Email
    public String email;

    @Constraints.Required
    @Constraints.MinLength(3)
    private String password;

    @Constraints.Min(18)
    @Constraints.Max(99)
    public Integer age;


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

    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if(username.equals("admin"))
           errors.add(new ValidationError("username","username draf nicht admin sein"));

        if(password.equals(username))
            errors.add(new ValidationError("password", "passwort muss != username sein"));

        return errors;
    }


    @Override
    public Optional<User> bind(String key, Map<String, String[]> data) {
        Logger.info("bind "+ data.toString());
        try {
            username = new String(data.get("username")[0]);
            email = new String(data.get("email")[0]);
            age = new Integer((data.get("age")[0]));
            return Optional.of(this);

        } catch (Exception e) { // no parameter match return None
            return Optional.empty();
        }
    }

    @Override
    public String unbind(String key) {
        return new StringBuilder()
                .append("username=")
                .append(username)
                .append("&email=")
                .append(email)
                .append("&age=")
                .append(age)
                .toString();
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}

package controllers;

import java.io.File;
import java.util.Map;
import java.util.Optional;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import validation.userchecks.CreateUserCheck;
import validation.userchecks.EditUserCheck;
import validation.userchecks.LoginUserCheck;
import play.mvc.QueryStringBindable;
import validation.validators.CustomValidators;

import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

@Constraints.Validate(groups = {CreateUserCheck.class, EditUserCheck.class})
public class User implements Constraints.Validatable<List<ValidationError>>, QueryStringBindable<User> {


    private Integer userId;

    @Constraints.Required(groups = {CreateUserCheck.class, LoginUserCheck.class})
    //@Constraints.MaxLength(value = 20, groups = {CreateUserCheck.class, EditUserCheck.class})
    //@CustomValidators.AllUpperCase(groups = {CreateUserCheck.class, EditUserCheck.class})
    private String username;

    @Constraints.Email(groups = {CreateUserCheck.class,EditUserCheck.class, LoginUserCheck.class})
    private List<String> emails;

    @Constraints.Required(groups = {CreateUserCheck.class, LoginUserCheck.class})
    //@Constraints.MinLength(value = 3, groups = {CreateUserCheck.class, EditUserCheck.class})
    //@Constraints.ValidateWith(value = CustomValidators.AllUpperCaseValidator.class, groups = {CreateUserCheck.class, EditUserCheck.class})
    private String password;


    //@Constraints.Required(groups = {CreateUserCheck.class})
    //@Constraints.Min(value = 18, groups = {CreateUserCheck.class, EditUserCheck.class})
    //@Constraints.Max(value = 99, groups = {CreateUserCheck.class, EditUserCheck.class})
    private Integer age;

    private Boolean admin;


    @Override
    public String toString() {
        return username + " " + admin;
    }


    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (username.equals("admin") && admin == null)
            errors.add(new ValidationError("username", "username darf nicht admin sein"));

        if (password.equals(username))
            errors.add(new ValidationError("password", "passwort muss != username sein"));

        return errors;
    }


    @Override
    public Optional<User> bind(String key, Map<String, String[]> data) {
        try {
            username = new String(data.get("username")[0]);
            emails = new ArrayList<String>();
            emails.add(new String(data.get("email")[0]));
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
                .append(emails)
                .append("&age=")
                .append(age)
                .toString();
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

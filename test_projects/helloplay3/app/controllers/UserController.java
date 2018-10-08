package controllers;


import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserController extends Controller {


    private final Form<User> userForm;


    private static List<User> users = new ArrayList<>();

    protected static Optional<User> findUser(Integer userId) {
        return users.stream().filter(u -> u.getUserId().equals(userId)).findFirst();
    }

    @Inject
    public UserController(FormFactory formFactory) {
        this.userForm = formFactory.form(User.class);

        User testUser = new User();
        testUser.setUserId(0);
        testUser.setUsername("testUser");
        testUser.setPassword("1234");
        testUser.setAge(22);
        testUser.setEmail("mail@example.org");
        users.add(testUser);
    }

    public Result home() {
        return redirect(controllers.routes.UserController.getUsers());
    }


    public Result getUsers() {
        return ok(users.toString());
    }

    public Result getUser(Integer userId) {
        Optional<User> user = findUser(userId);

        if (user.isPresent()) {
            return ok(user.get().toString());
        } else {
            return badRequest("user does not exist");
        }

    }

    public Result showCreateUserForm() {
        return ok(views.html.create_user.render(userForm));

    }

    public Result createUser() {

        Form<User> boundForm = userForm.bindFromRequest("username","password","email","age");

        if(boundForm.hasErrors()){
            return ok(views.html.create_user.render(boundForm));
        }

        User newUser = boundForm.get();
        newUser.setUserId(users.size());
        users.add(newUser);

        return ok("New user:  " + newUser.toString());
    }

    public Result showEditUserForm(Integer userId) {

        Optional<User> user = findUser(userId);

        if(user.isPresent()){
            return ok(views.html.edit_user.render(userForm.fill(user.get())));
        }
        return badRequest("user does not exist");
    }

    public Result editUser() {

        Form<User> boundForm = userForm.bindFromRequest("userId", "username", "password", "email", "age");


        if(boundForm.hasErrors()){
            return ok(views.html.edit_user.render(boundForm));
        }

        User changedUser = boundForm.get();
        Optional<User> storedUser = findUser(changedUser.getUserId());

        if(storedUser.isPresent()){
            users.remove(storedUser.get());
            users.add(changedUser);

            return ok("changed user: " + changedUser.toString());
        }
        else{
            return badRequest("user does not exist");
        }
    }

    public Result user(User user){
        return ok(user.getUsername());
    }

    public Result age(AgeRange ageRange){
        Optional<User> storedUser = users.stream().filter(u -> u.getAge().compareTo(ageRange.from) >= 0 &&
                u.getAge().compareTo(ageRange.to) <= 0).findFirst();
        return ok(storedUser.toString());
    }

    public Result list(List<String> tags) {
        return ok(tags.toString());
    }

}
package controllers;


import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserController extends Controller {


    private final Form<User> userForm;


    private List<User> users = new ArrayList<>();

    private Optional<User> findUser(Integer userId) {
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
        Form<User> boundForm = userForm.bindFromRequest("username", "password", "email", "age");


        if (boundForm.hasErrors()) {
            return badRequest(views.html.create_user.render(boundForm));
        } else {
            User user = boundForm.get();
            user.setUserId(users.size());

            users.add(user);
            return ok("New User: " + user.toString());
        }
    }



    public Result showEditUserForm(Integer userId) {

        Optional<User> user = findUser(userId);

        if (user.isPresent()) {
            Form<User> filledForm = userForm.fill(user.get());
            return ok(views.html.edit_user.render(filledForm));
        } else {
            return badRequest("user does not exist");
        }
    }


    public Result editUser() {
        Form<User> boundForm = userForm.bindFromRequest("userId", "username", "password", "email", "age");


        if (boundForm.hasErrors()) {
            return badRequest(views.html.edit_user.render(boundForm));
        } else {

            User submittedUser = boundForm.get();
            Optional<User> storedUser = findUser(submittedUser.getUserId());


            if (storedUser.isPresent()) {
                users.remove(storedUser.get());
                users.add(submittedUser);
                return ok("Edited User: " + storedUser.toString());

            } else {
                return badRequest("user does not exist");
            }
        }
    }





}

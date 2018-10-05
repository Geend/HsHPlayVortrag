package controllers;


import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class UserController extends Controller {


    private final Form<User> userForm;


    private List<User> users = new ArrayList<>();

    @Inject
    public UserController(FormFactory formFactory) {
        this.userForm = formFactory.form(User.class);
    }

    public Result home(){
        return redirect(controllers.routes.UserController.getUsers());
    }

    public Result postTest(Integer id) {
        return ok("id ist " + id);
    }

    public Result showCreateUserForm() {
        return ok(views.html.user.render(userForm));
    }



    public Result showEditUserForm(Integer userId){

        Optional<User> user =  users.stream().filter(u -> u.getUserId().equals(userId)).findFirst();

        if(user.isPresent()) {

                Form<User> filledForm = userForm.fill(user.get());
            return ok(views.html.user.render(filledForm));
        }

        else{
            return badRequest("user does not exist");
        }
    }



    public Result getUsers() {
        return ok(users.toString());
    }

    public Result getUser(Integer userId) {

        if (userId != 0) {
            return ok("userID: " + userId);
        } else
            return ok(views.html.user.render(userForm));


    }

    public Result createUser() {
        Form<User> boundForm = userForm.bindFromRequest("username", "password", "email", "age");


        if (boundForm.hasErrors()) {
            return badRequest(views.html.user.render(boundForm));
        } else {
            User user = boundForm.get();
            user.setUserId(users.size());

            users.add(user);
            return ok("New User " + user.getUsername() + " " + user.getPassword());
        }
    }


    public Result editUser() {
        Form<User> boundForm = userForm.bindFromRequest("username", "password", "email", "age");


        if (boundForm.hasErrors()) {
            return badRequest(views.html.user.render(boundForm));
        } else {
            User user = boundForm.get();


            users.add(user);
            return ok("New User " + user.getUsername() + " " + user.getPassword());
        }
    }

}

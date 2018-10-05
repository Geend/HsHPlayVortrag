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

        User testUser = new User();
        testUser.setUserId(0);
        testUser.setUsername("test");
        testUser.setPassword("1234");
        testUser.setAge(22);
        testUser.setEmail("mail@example.org");
        users.add(testUser);
    }

    public Result home(){
        return redirect(controllers.routes.UserController.getUsers());
    }

    public Result postTest(Integer id) {
        return ok("id ist " + id);
    }

    public Result showCreateUserForm() {
        return ok(views.html.create_user.render(userForm));
    }



    public Result showEditUserForm(Integer userId){

        Optional<User> user =  users.stream().filter(u -> u.getUserId().equals(userId)).findFirst();

        if(user.isPresent()) {

                Form<User> filledForm = userForm.fill(user.get());
            return ok(views.html.edit_user.render(filledForm));
        }

        else{
            return badRequest("user does not exist");
        }
    }



    public Result getUsers() {
        return ok(users.toString());
    }

    public Result getUser(Integer userId) {
        //TODO: search user
        if (userId != 0) {
            return ok("userID: " + userId);
        } else
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
            return ok("New User " + user.getUsername() + " " + user.getPassword());
        }
    }


    public Result editUser() {
        Form<User> boundForm = userForm.bindFromRequest("userId", "username", "password", "email", "age");


        if (boundForm.hasErrors()) {
            return badRequest(views.html.edit_user.render(boundForm));
        } else {
            User submittedUser = boundForm.get();
            //Optional<User> storedUser =  users.stream().filter(u -> u.getUserId().equals(userId)).findFirst();

            Optional<User> oldUser = users.stream().filter(u -> u.getUserId() == submittedUser.getUserId()).findFirst();


            if(oldUser.isPresent()){
                users.remove(oldUser.get());
                users.add(submittedUser);
                return ok("Edit User " + submittedUser.getUsername() + " " + submittedUser.getPassword());

            }
            else
            {
                return badRequest("user does not exist");
            }
           // users.add(create_user);
        }
    }

}

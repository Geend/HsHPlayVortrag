package controllers;


import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import validation.userchecks.CreateUserCheck;
import validation.userchecks.EditUserCheck;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserController extends Controller {


    private static List<User> users = new ArrayList<>();

    private static void createTestUser(){
        User testUser = new User();
        testUser.setUserId(0);
        testUser.setUsername("testUser");
        testUser.setPassword("1234");
        testUser.setAge(22);

        List<String> emails = new ArrayList<>();
        emails.add("mail1@example.org");
        emails.add("mail2@example.org");
        testUser.setEmails(emails);
        testUser.setAdmin(true);
        users.add(testUser);

    }

    protected static Optional<User> findUser(Integer userId) {
        return users.stream().filter(u -> u.getUserId().equals(userId)).findFirst();
    }


    protected static List<User> findUsersByName(String name) {
        List<User> storedUsers = users.stream().filter(u -> u.getUsername().equals(name)).collect(Collectors.toList());
        return storedUsers;
    }

    protected static List<User> findUsers(String name, String email, Integer age) {
        List<User> storedNames = users.stream().filter(u -> u.getUsername().equals(name)).collect(Collectors.toList());
        List<User> storedEmail = storedNames.stream().filter(u -> u.getEmails().get(0).equals(email)).collect(Collectors.toList());
        List<User> storedAge = storedEmail.stream().filter(u -> u.getAge().equals(age)).collect(Collectors.toList());
        return storedAge;
    }



    private final Form<User> userForm;

    private final Form<User> createFrom;
    private final Form<User> editForm;

    @Inject
    public UserController(FormFactory formFactory) {
        this.userForm = formFactory.form(User.class);
        this.createFrom = formFactory.form(User.class, CreateUserCheck.class);
        this.editForm = formFactory.form(User.class, EditUserCheck.class);

        createTestUser();
    }

    public Result home() {
        return redirect(controllers.routes.UserController.getUsers());
    }

    //-------------------------
    //------- Shows Users ------
    //-------------------------
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


    //-------------------------
    //------ Create Users -----
    //-------------------------
    public Result showCreateUserForm() {
        return ok(views.html.create_user.render(createFrom));

    }

    public Result createUser() {

        Form<User> boundForm = createFrom.bindFromRequest("username","password","emails","age","admin");

        if(boundForm.hasErrors()){
            return ok(views.html.create_user.render(boundForm));
        }

        User newUser = boundForm.get();
        newUser.setUserId(users.size());
        users.add(newUser);

        return ok("New user: " + newUser.toString());
    }


    //-------------------------
    //------- Edit Users ------
    //-------------------------
    public Result showEditUserForm(Integer userId) {

        Optional<User> user = findUser(userId);

        if(user.isPresent()){
            return ok(views.html.edit_user.render(editForm.fill(user.get())));
        }
        return badRequest("user does not exist");
    }

    public Result editUser() {

        Form<User> boundForm = editForm.bindFromRequest("userId", "username", "password", "emails", "age","admin");

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


    //-------------------------
    //--- Data Binding Demo ---
    //-------------------------
    public Result age(AgeRange ageRange){
       List<User> userList = users.stream().filter(u -> u.getAge().compareTo(ageRange.from) >= 0 &&
                u.getAge().compareTo(ageRange.to) <= 0).collect(Collectors.toList());


        return ok(userList.toString());
    }

    public Result list(List<String> tags) {
        return ok(tags.toString());
    }

    public Result getUserByAttr(User user) {
        Logger.info(user.toString());
        String username = user.getUsername();
        String email = user.getEmails().get(0);
        Integer age = user.getAge();
        return ok(findUsers(username, email, age).toString());
    }

    public Result upload() throws IOException {
        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("file");
        final File file = filePart.getFile();
        return ok("file size = " + file.length() + " bytes");
    }

}

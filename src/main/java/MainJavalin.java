import io.javalin.Javalin;
import controller.UserService;

public class MainJavalin {
    public static void main(String[] args) {
        UserService userService = new UserService();

        var app = Javalin.create(config -> {
            config.defaultContentType = "text/plain";
        }).start(7070);

        app.post("/login", userService::login);
        app.post("/createUser", userService::createUser);
        app.post("/updateUser", userService::updateUser);
        app.post("/deleteUser", userService::deleteUser);
        app.post("/getUsers", userService::getUsers);
        app.post("/getUserById", userService::getUserById);
    }
}

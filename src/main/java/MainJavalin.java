import io.javalin.Javalin;
import controller.UserService;

public class MainJavalin {
    public static void main(String[] args) {
        UserService userService = new UserService();

        var app = Javalin.create(config -> {
            config.defaultContentType = "text/plain";
        }).start(7070);

        app.post("/login", userService::login);
        app.put("/createUser", userService::createUser);
        app.patch("/updateUser", userService::updateUser);
        app.delete("/deleteUser", userService::deleteUser);
        app.get("/getUsers", userService::getUsers);
        app.post("/getUserById", userService::getUserById);
    }
}

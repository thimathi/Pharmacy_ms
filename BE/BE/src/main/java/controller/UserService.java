package controller;

import io.javalin.http.Context;
import modules.CreateUserRequest;
import modules.GetUserByIdRequest;
import modules.LoginRequest;
import modules.UpdateUserRequest;
import services.Database;
import services.TokenDecryptor;
import services.TokenGenerator;
import services.TokenValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class UserService {
    public void createUser(Context ctx) {
        Boolean request = TokenValidator.validateToken(ctx);
        if (request) {
            try{
                CreateUserRequest requestData = ctx.bodyAsClass(CreateUserRequest.class);
                String sql = "INSERT INTO user_details (name, address, mobile, email, user_type, username, password) VALUES (?, ?, ?, ?, ?,?,?)";

                try (Connection conn = Database.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    stmt.setString(1, requestData.getName());
                    stmt.setString(2, requestData.getAddress());
                    stmt.setString(3, requestData.getMobile());
                    stmt.setString(4, requestData.getEmail());
                    stmt.setInt(5, requestData.getUserTypeId());
                    stmt.setString(6, requestData.getEmail());
                    String randomValue = UUID.randomUUID().toString();
                    stmt.setString(7, randomValue);

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                int newUserId = generatedKeys.getInt(1);
                                ctx.status(201).json("User created successfully with ID: " + newUserId);
                            } else {
                                ctx.status(500).json("Failed to create user, no ID obtained.");
                            }
                        }
                    } else {
                        ctx.status(400).json("No user was created, check your input data.");
                    }
                }

            }catch (Exception e){
                ctx.status(400).json("Bad Request" + e.getMessage());
            }
        }else{
            ctx.status(400).json("Bad Request");
        }
    }

    public void updateUser(Context ctx) {
        Boolean request = TokenValidator.validateToken(ctx);
        if (request) {
            try {

                UpdateUserRequest requestData = ctx.bodyAsClass(UpdateUserRequest.class);
                if (requestData.getId() == null) {
                    ctx.status(400).json("User ID is required for update.");
                    return;
                }

                List<String> updates = new ArrayList<>();
                List<Object> values = new ArrayList<>();

                if (requestData.getUsername() != null) {
                    updates.add("username = ?");
                    values.add(requestData.getUsername());
                }
                if (requestData.getPassword() != null) {
                    updates.add("password = ?");
                    values.add(requestData.getPassword());
                }
                if (requestData.getEmail() != null) {
                    updates.add("email = ?");
                    values.add(requestData.getEmail());
                }
                if (requestData.getMobile() != null) {
                    updates.add("mobile = ?");
                    values.add(requestData.getMobile());
                }
                if (requestData.getAddress() != null) {
                    updates.add("address = ?");
                    values.add(requestData.getAddress());
                }
                if (requestData.getUserTypeId() != null) {
                    updates.add("user_type = ?");
                    values.add(requestData.getUserTypeId());
                }

                if (updates.isEmpty()) {
                    ctx.status(400).json("No valid fields provided for update.");
                }

                String sql = "UPDATE user_details SET " + String.join(", ", updates) + " WHERE id = ?";
                values.add(requestData.getId());

                try (Connection conn = Database.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    for (int i = 0; i < values.size(); i++) {
                        stmt.setObject(i + 1, values.get(i));
                    }

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        ctx.status(200).json("User updated successfully.");
                    } else {
                        ctx.status(404).json("User not found or no changes made.");
                    }
                } catch (Exception e) {
                    ctx.status(500).json("Failed to update user details: " + e.getMessage());
                }

            } catch (Exception e) {
                ctx.status(400).json("Bad Request" + e.getMessage());
            }
        }else{
            ctx.status(400).json("Bad Request");
        }
    }

    public void deleteUser(Context ctx) {
        Boolean request = TokenValidator.validateToken(ctx);
        if (request) {
            try {
                GetUserByIdRequest credentials = ctx.bodyAsClass(GetUserByIdRequest.class);
                String userId = credentials.getUserId();
                int userIdInt;
                try {
                    userIdInt = Integer.parseInt(userId);
                } catch (NumberFormatException e) {
                    ctx.status(400).json("Bad Request");
                    return;
                }

                String sql = "DELETE FROM user_details WHERE id = ?";

                try (Connection conn = Database.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, userIdInt);

                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        ctx.status(200).json("User deleted successfully.");
                    } else {
                        ctx.status(404).json("User not found.");
                    }
                } catch (NumberFormatException e) {
                    ctx.status(400).json("Invalid ID format.");
                } catch (Exception e) {
                    ctx.status(500).json("Failed to delete user: " + e.getMessage());
                }


            } catch (Exception e) {
                ctx.status(400).json("Bad Request" + e.getMessage());
            }
        }else{
            ctx.status(400).json("Bad Request");
        }
    }

    public void getUserById(Context ctx) {
        Boolean request = TokenValidator.validateToken(ctx);
        if (request) {
            try {
                GetUserByIdRequest credentials = ctx.bodyAsClass(GetUserByIdRequest.class);
                String userId = credentials.getUserId();
                int userIdInt;
                try {
                    userIdInt = Integer.parseInt(userId);
                } catch (NumberFormatException e) {
                    ctx.status(400).json("Bad Request");
                    return;
                }
                String sql = "SELECT ud.id, ud.name, ud.address, ud.mobile, ud.email,ud.username,ud.password, ut.role " +
                        "FROM user_details ud " +
                        "JOIN user_type ut ON ud.user_type = ut.id " +
                        "WHERE ud.id = ?";
                List<Map<String, Object>> users = new ArrayList<>();

                try (Connection conn = Database.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setInt(1, userIdInt);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            Map<String, Object> user = new HashMap<>();
                            user.put("id", rs.getInt("id"));
                            user.put("name", rs.getString("name"));
                            user.put("address", rs.getString("address"));
                            user.put("mobile", rs.getString("mobile"));
                            user.put("email", rs.getString("email"));
                            user.put("usertype", rs.getString("role"));

                            ctx.json(user);
                        } else {
                            ctx.status(404).json("User not found");
                        }
                    }
                } catch (Exception e) {
                    ctx.status(500).json("Failed to retrieve user details: " + e.getMessage());
                }

            } catch (Exception e) {
                ctx.status(400).json("Bad Request");
            }
        }else{
            ctx.status(400).json("Bad Request");
        }
    }

    public void getUsers(Context ctx) {
        Boolean request = TokenValidator.validateToken(ctx);
        if (request) {
            try {
                String sql = "SELECT ud.id, ud.name, ud.address, ud.mobile, ud.email, ud.username, ud.password, ut.role " +
                        "FROM user_details ud " +
                        "JOIN user_type ut ON ud.user_type = ut.id";
                List<Map<String, Object>> users = new ArrayList<>();

                try (Connection conn = Database.connect();
                     PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("id", rs.getInt("id"));
                        user.put("name", rs.getString("name"));
                        user.put("address", rs.getString("address"));
                        user.put("mobile", rs.getString("mobile"));
                        user.put("email", rs.getString("email"));
                        user.put("usertype", rs.getString("role"));
                        user.put("username", rs.getString("username"));
                        user.put("password", rs.getString("password"));
                        users.add(user);
                    }

                    ctx.status(200).json(users);
                } catch (Exception e) {
                    ctx.status(500).json("Failed to retrieve user details: " + e.getMessage());
                }

            } catch (Exception e) {
                ctx.status(400).json("Bad Request");
            }
        }else{
            ctx.status(400).json("Bad Request");
        }
    }

    public void login(Context ctx) {
        try {
            LoginRequest credentials = ctx.bodyAsClass(LoginRequest.class);
            String username = credentials.getUname();
            String password = credentials.getPword();

            String sql = "SELECT * FROM user_details WHERE username = ? AND password = ?";

            try (Connection conn = Database.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String user = rs.getString("id");
                    String userType = rs.getString("user_type");
                    String token = TokenGenerator.generateToken(userType, user);

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Active User");
                    response.put("token", token);

                    ctx.status(200).json(response);

                } else {
                    ctx.status(403).json("Invalid credentials");
                }
            }
        } catch (Exception e) {
            ctx.status(403).json("Bad Request");
        }
    }
}

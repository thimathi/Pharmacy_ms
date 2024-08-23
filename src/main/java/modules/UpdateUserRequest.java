package modules;

public class UpdateUserRequest {
    private Integer userId;
    private String username;
    private String password;
    private String email;
    private String mobile;
    private String address;
    private Integer userTypeId;
    private String name; // Add this line if 'name' is expected in the JSON

    // Getters and setters
    public Integer getId() {
        return userId;
    }

    public void setId(Integer id) {
        this.userId = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getName() {  // Ensure these accessors are in place
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

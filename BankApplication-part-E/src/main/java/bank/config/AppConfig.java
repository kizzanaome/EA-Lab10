package bank.config;


import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class AppConfig {

    @NotBlank
    private String name;

    @NotBlank
    private String version;

    private Server server = new Server();
    private User user = new User();

    // --- Nested Classes ---
    public static class Server {
        @NotBlank
        private String url;
        private String name;

        // getters and setters
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class User {
        private String firstname;
        private String lastname;

        @NotBlank
        @Size(min = 8, max = 15)
        private String username;

        @NotBlank
        @Size(min = 8, max = 15)
        private String password;

        private List<String> countries;

        // getters and setters
        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }
        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public List<String> getCountries() { return countries; }
        public void setCountries(List<String> countries) { this.countries = countries; }
    }

    // --- Getters and setters for main class ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public Server getServer() { return server; }
    public void setServer(Server server) { this.server = server; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    // --- Print configuration after loading ---
    @PostConstruct
    public void printProperties() {
        System.out.println("Application Name: " + name);
        System.out.println("Version: " + version);
        System.out.println("Server URL: " + server.url);
        System.out.println("Server Name: " + server.name);
        System.out.println("User: " + user.firstname + " " + user.lastname);
        System.out.println("Username: " + user.username);
        System.out.println("Countries: " + user.countries);
    }
}

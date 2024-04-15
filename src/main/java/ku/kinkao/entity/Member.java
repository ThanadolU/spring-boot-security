package ku.kinkao.entity;

import jakarta.persistence.Convert;
import ku.kinkao.config.AttributeEncryptor;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
public class Member {

    @Id
    @GeneratedValue
    private UUID id;

    @Convert(converter = AttributeEncryptor.class)
    private String username;
    private String password;
    @Convert(converter = AttributeEncryptor.class)
    private String firstName;
    @Convert(converter = AttributeEncryptor.class)
    private String lastName;
    @Convert(converter = AttributeEncryptor.class)
    private String email;
    private String role;

    private Instant createdAt;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String roleUser) {
        this.role = roleUser;
    }

    public String getRole() {
        return role;
    }
}

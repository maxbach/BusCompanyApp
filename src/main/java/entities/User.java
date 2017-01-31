package entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by maxbacinskiy on 23.01.17.
 */

@Entity
@Table(name = "entities")
public class User {

    @Id @GeneratedValue
    @Column(name = "id")
    private long id;

    @JoinColumn(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_birth")
    private Date dateOfBirth;

    public User(String userName, String password, boolean isAdmin,
                String firstName, String secondName, Date dateOfBirth) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.firstName = firstName;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}

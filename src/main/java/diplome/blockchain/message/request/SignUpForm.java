package diplome.blockchain.message.request;

import diplome.blockchain.model.Diploma;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank
    @Size(min=3, max = 60)
    private String firstname;

    @NotBlank
    @Size(min=3, max = 60)
    private String lastname;

    @NotBlank
    @Size(min=3, max = 60)
    private String email;
    private List<Diploma> diploma;
    @NotBlank
    @Size(min=3, max = 60)
    private String telephone;
    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Diploma> getDiploma() {
        return diploma;
    }

    public void setDiploma(List<Diploma> diploma) {
        this.diploma = diploma;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

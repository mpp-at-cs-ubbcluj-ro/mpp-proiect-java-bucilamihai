package domain;

import java.util.Objects;

public class OfficeResponsable extends Entity{
    private String username;
    private String password;

    public OfficeResponsable(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeResponsable that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password);
    }

    @Override
    public String toString() {
        return "OfficeResponsable{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

package umc.spring.domain.emums;

public enum Role {
    ADMIN, USER;

    public String getSecurityRole() {
        return "ROLE_" + this.name();
    }
}

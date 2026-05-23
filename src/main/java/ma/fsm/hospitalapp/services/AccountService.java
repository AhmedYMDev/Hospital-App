package ma.fsm.hospitalapp.services;

import ma.fsm.hospitalapp.entities.AppRole;
import ma.fsm.hospitalapp.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    AppUser loadUserByUsername(String username);
}

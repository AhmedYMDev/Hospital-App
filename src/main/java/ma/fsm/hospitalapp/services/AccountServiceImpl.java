package ma.fsm.hospitalapp.services;

import lombok.RequiredArgsConstructor;
import ma.fsm.hospitalapp.entities.AppRole;
import ma.fsm.hospitalapp.entities.AppUser;
import ma.fsm.hospitalapp.repositories.AppRoleRepository;
import ma.fsm.hospitalapp.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser existingUser = appUserRepository.findByUsername(username);
        if (existingUser != null) {
            return existingUser;
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas");
        }

        AppUser appUser = new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setEmail(email);
        appUser.setRoles(new ArrayList<>());
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole existingRole = appRoleRepository.findById(role).orElse(null);
        if (existingRole != null) {
            return existingRole;
        }

        AppRole appRole = new AppRole();
        appRole.setRole(role);
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            throw new IllegalArgumentException("Utilisateur introuvable : " + username);
        }

        AppRole appRole = appRoleRepository.findById(role)
                .orElseThrow(() -> new IllegalArgumentException("Role introuvable : " + role));

        boolean alreadyHasRole = appUser.getRoles()
                .stream()
                .anyMatch(r -> r.getRole().equals(role));

        if (!alreadyHasRole) {
            appUser.getRoles().add(appRole);
        }
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}

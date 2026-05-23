package ma.fsm.hospitalapp.repositories;

import ma.fsm.hospitalapp.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}

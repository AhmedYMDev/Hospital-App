package ma.fsm.hospitalapp;

import ma.fsm.hospitalapp.entities.Patient;
import ma.fsm.hospitalapp.repositories.PatientRepository;
import ma.fsm.hospitalapp.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class HospitalAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository, AccountService accountService) {
        return args -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (patientRepository.count() == 0) {
                patientRepository.save(new Patient(null, "Hassan", dateFormat.parse("1998-01-15"), false, 120));
                patientRepository.save(new Patient(null, "Mohammed", dateFormat.parse("1987-07-22"), true, 320));
                patientRepository.save(new Patient(null, "Yasmine", dateFormat.parse("2001-03-10"), false, 210));
            }

            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");

            accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");
            accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");

            accountService.addRoleToUser("user1", "USER");
            accountService.addRoleToUser("admin", "USER");
            accountService.addRoleToUser("admin", "ADMIN");
        };
    }
}

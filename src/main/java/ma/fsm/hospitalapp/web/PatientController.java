package ma.fsm.hospitalapp.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.fsm.hospitalapp.entities.Patient;
import ma.fsm.hospitalapp.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword, PageRequest.of(page, size));

        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePatients.getTotalPages());
        model.addAttribute("totalItems", pagePatients.getTotalElements());
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);

        return "patients";
    }

    @GetMapping("/admin/deletePatient")
    public String deletePatient(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "size", defaultValue = "5") int size) {
        patientRepository.deleteById(id);
        return redirectToIndex(page, keyword, size);
    }

    @GetMapping("/admin/formPatient")
    public String formPatient(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword,
                              @RequestParam(name = "size", defaultValue = "5") int size) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "formPatient";
    }

    @PostMapping("/admin/save")
    public String save(@Valid Patient patient,
                       BindingResult bindingResult,
                       Model model,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "keyword", defaultValue = "") String keyword,
                       @RequestParam(name = "size", defaultValue = "5") int size) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("page", page);
            model.addAttribute("keyword", keyword);
            model.addAttribute("size", size);
            return patient.getId() == null ? "formPatient" : "editPatient";
        }

        patientRepository.save(patient);
        return redirectToIndex(page, keyword, size);
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model,
                              @RequestParam(name = "id") Long id,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient introuvable"));

        model.addAttribute("patient", patient);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "editPatient";
    }

    private String redirectToIndex(int page, String keyword, int size) {
        String encodedKeyword = UriUtils.encode(keyword == null ? "" : keyword, StandardCharsets.UTF_8);
        return "redirect:/user/index?page=" + page + "&size=" + size + "&keyword=" + encodedKeyword;
    }
}

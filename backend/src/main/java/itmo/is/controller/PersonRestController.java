package itmo.is.controller;

import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.request.CreatePersonRequest;
import itmo.is.dto.domain.request.UpdatePersonRequest;
import itmo.is.model.domain.ImportHistory;
import itmo.is.model.domain.Person;
import itmo.is.model.security.Role;
import itmo.is.model.security.User;
import itmo.is.service.domain.ImportHistoryService;
import itmo.is.service.domain.ImportService;
import itmo.is.service.domain.PersonService;
import itmo.is.service.security.authorization.PersonSecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/people")
@CrossOrigin(origins = "*", allowedHeaders = "Authorization")
@RequiredArgsConstructor
public class PersonRestController {
    private final PersonService personService;
    private final ImportService personImportService;
    private final ImportHistoryService importHistoryService;
    private final PersonSecurityService personSecurityService;

    @GetMapping

    public ResponseEntity<Page<PersonDto>> getAllPeople(
            @RequestParam(value = "name", required = false) String name,
            Pageable pageable
    ) {
        log.info("getAllPeople method started");
        Page<PersonDto> people = personService.findAllPeople(name, pageable);
        log.info("getAllPeople method finished");
        return ResponseEntity.ok(people);
    }
    /**
     * Метод для массового импорта объектов PersonDto из JSON файла.
     *
     * @param file Файл JSON с данными объектов PersonDto
     * @return Ответ с результатом импорта
     */
    @PostMapping("/import")
    public ResponseEntity<String> importPersons(@RequestParam("file") MultipartFile file) {
        User user = personSecurityService.getCurrentUser();

        try {
            log.info("importPersons method started");
            File tempFile = File.createTempFile("import-", ".json");
            file.transferTo(tempFile);

            List<Person> persons = personImportService.importPersonsFromFile(tempFile);
            personService.importPersonFromFile(persons);

            // Записываем успешный импорт в историю
            importHistoryService.recordImportHistory(user.getId(), "SUCCESS", persons.size(),user.getUsername());

            log.info("importPersons method finished");
            return ResponseEntity.ok("Successfully imported " + persons.size() + " persons.");
        } catch (IllegalArgumentException e) {
            log.error("Validation error during import", e);
            importHistoryService.recordImportHistory(user.getId(), "FAILED", 0, user.getUsername());
            return ResponseEntity.status(400).body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error during import", e);
            importHistoryService.recordImportHistory(user.getId(), "FAILED", 0,user.getUsername());
            return ResponseEntity.status(500).body("Failed to import persons: " + e.getMessage());
        }
    }



    @GetMapping("/{id}")

    public ResponseEntity<PersonDto> getPersonById(@PathVariable int id) {
        PersonDto person = personService.findPersonById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping

    public ResponseEntity<PersonDto> createPerson(@RequestBody CreatePersonRequest request) {
        log.info("createPerson method started");
        PersonDto createdPerson = personService.createPerson(request);

        log.info("createPerson method finished");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);

    }

    @PreAuthorize("@personSecurityService.hasEditRights(#id)")
    @PutMapping("/{id}")

    public ResponseEntity<PersonDto> updatePerson(
            @PathVariable int id,
            @RequestBody UpdatePersonRequest request
    ) {
        log.info("updatePerson method started");
        PersonDto updatedPerson = personService.updatePerson(id, request);
        log.info(" updatePerson method finished");
        return ResponseEntity.ok(updatedPerson);
    }

    @PreAuthorize("@personSecurityService.hasEditRights(#id)")
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        log.info("deletePerson method started");
        personService.deletePerson(id);
        log.info("deletePerson method finished");
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@personSecurityService.isOwner(#id)")
    @PutMapping("/{id}/allow-admin-editing")
    public ResponseEntity<Void> allowAdminEditing(@PathVariable int id) {
        personService.allowAdminEditing(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@personSecurityService.isOwner(#id)")
    @PutMapping("/{id}/deny-admin-editing")

    public ResponseEntity<Void> denyAdminEditing(@PathVariable int id) {
        personService.denyAdminEditing(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/import/history")
    public ResponseEntity<List<ImportHistory>> getImportHistory() {
        User user = personSecurityService.getCurrentUser();

        if (user.getRole() == Role.ROLE_ADMIN) {
            return ResponseEntity.ok(importHistoryService.getAllImportHistory());
        } else {
            return ResponseEntity.ok(importHistoryService.getImportHistoryForUser(user.getId()));
        }
    }


}

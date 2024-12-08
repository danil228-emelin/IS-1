package itmo.is.controller;

import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.request.CreatePersonRequest;
import itmo.is.dto.domain.request.UpdatePersonRequest;
import itmo.is.dto.domain.response.CountResponse;
import itmo.is.dto.domain.response.PercentageResponse;
import itmo.is.model.domain.Color;
import itmo.is.model.domain.Country;
import itmo.is.service.domain.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/people")
@CrossOrigin(origins = "*", allowedHeaders = "Authorization")
@RequiredArgsConstructor
public class PersonRestController {
    private final PersonService personService;

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

    @PreAuthorize("@personSecurityService.isOwner(#id)")
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

    @GetMapping("/count-by-weight")

    public ResponseEntity<CountResponse> countPeopleByWeight(@RequestParam Integer weight) {
        CountResponse response = personService.countPeopleByExactWeight(weight);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-weight-less-than")

    public ResponseEntity<CountResponse> countPeopleByWeightLessThan(@RequestParam Integer weight) {
        CountResponse response = personService.countPeopleByWeightLessThan(weight);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/nationalities")

    public ResponseEntity<List<Country>> getDistinctNationalities() {
        List<Country> nationalities = personService.findDistinctNationalities();
        return ResponseEntity.ok(nationalities);
    }


    @GetMapping("/percentage-by-eye-color")

    public ResponseEntity<PercentageResponse> calculatePercentageByEyeColor(@RequestParam Color color) {
        PercentageResponse response = personService.calculatePercentageOfPeopleByEyeColor(color);
        return ResponseEntity.ok(response);
    }
}

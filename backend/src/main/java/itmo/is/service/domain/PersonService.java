package itmo.is.service.domain;

import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.request.CreatePersonRequest;
import itmo.is.dto.domain.request.UpdatePersonRequest;
import itmo.is.dto.domain.response.CountResponse;
import itmo.is.dto.domain.response.PercentageResponse;
import itmo.is.mapper.domain.PersonMapper;
import itmo.is.model.domain.Color;
import itmo.is.model.domain.Country;
import itmo.is.model.domain.Person;
import itmo.is.model.domain.StudyGroup;
import itmo.is.repository.PersonRepository;
import itmo.is.repository.StudyGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final StudyGroupRepository studyGroupRepository;

    private final PersonMapper personMapper;


    public Page<PersonDto> findAllPeople(String name, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return personRepository.findAllByNameContaining(name, pageable).map(personMapper::toDto);
        }
        return personRepository.findAll(pageable).map(personMapper::toDto);
    }

    public PersonDto findPersonById(int id) {
        return personRepository.findById(id).map(personMapper::toDto).orElseThrow();
    }

    public Person findPersonByIdNorDto(int id) {
        return personRepository.findById(id).orElseThrow();
    }

    public PersonDto createPerson(CreatePersonRequest request) {
        Person newOne = personMapper.toEntity(request);
        Optional<StudyGroup> studyGroup = studyGroupRepository.findById(request.study_id());

        if (studyGroup.isPresent()) {
            StudyGroup s = studyGroup.get();
            s.setStudentsCount(s.getStudentsCount() + 1);
            studyGroupRepository.save(s);
            newOne.setStudyGroup(s);
        }

        return personMapper.toDto(personRepository.save(newOne));
    }

    public PersonDto updatePerson(int id, UpdatePersonRequest request) {
        var person = personMapper.toEntity(request);
        person.setId(id);
        return personMapper.toDto(personRepository.save(person));
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    public void allowAdminEditing(int id) {
        var person = personRepository.findById(id).orElseThrow();
        person.setAdminEditAllowed(true);
        personRepository.save(person);
    }

    public void denyAdminEditing(int id) {
        var person = personRepository.findById(id).orElseThrow();
        person.setAdminEditAllowed(false);
        personRepository.save(person);
    }

    public CountResponse countPeopleByExactWeight(Integer weight) {
        return new CountResponse(personRepository.countByWeightEquals(weight));
    }

    public CountResponse countPeopleByWeightLessThan(Integer weight) {
        return new CountResponse(personRepository.countByWeightLessThan(weight));
    }

    public List<Country> findDistinctNationalities() {
        return personRepository.findDistinctNationalities();
    }


    public PercentageResponse calculatePercentageOfPeopleByEyeColor(Color color) {
        long total = personRepository.count();

        if (total == 0) {
            return new PercentageResponse(0);
        }

        long countByEyeColor = personRepository.countByEyeColorEquals(color);
        double percentage = (double) countByEyeColor / (double) total * 100;

        return new PercentageResponse(percentage);
    }
}

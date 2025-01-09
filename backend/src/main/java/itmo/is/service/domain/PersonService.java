package itmo.is.service.domain;

import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.request.CreatePersonRequest;
import itmo.is.dto.domain.request.UpdatePersonRequest;
import itmo.is.mapper.domain.PersonMapper;
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

    @Transactional
    public void importPersonFromFile(List<Person> persons) {
        for (Person person : persons) {
            validatePerson(person);
            if (person.getId() != 0) {
                updatePersonFromFile(person);
            } else {
                personRepository.save(person);

            }
        }

    }

    private void validatePerson(Person person) {
        // Проверка имени
        if (person.getName() == null || person.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (person.getName().length() < 2 || person.getName().length() > 50) {
            throw new IllegalArgumentException("Name must be between 2 and 50 characters.");
        }

        // Проверка координат
        if (person.getCoordinates().getX() < 0) {
            throw new IllegalArgumentException("Coordinate X cannot be negative.");
        }
        if (person.getCoordinates().getY() < -100 || person.getCoordinates().getY() > 100) {
            throw new IllegalArgumentException("Coordinate Y must be between -100 and 100.");
        }


        // Проверка местоположения
        if (person.getLocation().getX() < -1000 || person.getLocation().getX() > 1000) {
            throw new IllegalArgumentException("Location X must be between -1000 and 1000.");
        }
        if (person.getLocation().getY() < -1000 || person.getLocation().getY() > 1000) {
            throw new IllegalArgumentException("Location Y must be between -1000 and 1000.");
        }
        if (person.getLocation().getZ() < -1000 || person.getLocation().getZ() > 1000) {
            throw new IllegalArgumentException("Location Z must be between -1000 and 1000.");
        }

        // Проверка веса
        if (person.getWeight() <= 0) {
            throw new IllegalArgumentException("Weight must be greater than 0.");
        }
        if (person.getWeight() < 30 || person.getWeight() > 300) {
            throw new IllegalArgumentException("Weight must be between 30 and 300.");
        }

    }

    public void updatePersonFromFile(Person person) {
        Optional<StudyGroup> studyGroup = Optional.empty();
        if (person.getStudyGroup() != null) {
            studyGroup = studyGroupRepository.findById(person.getStudyGroup().getId());
        }
        Optional<Person> personBD = personRepository.findById(person.getId());
        if (personBD.isPresent()) {
            Person p_current_in_db = personBD.get();
            StudyGroup psg_current = p_current_in_db.getStudyGroup();
            if (psg_current == null) {
                StudyGroup s;
                if (studyGroup.isPresent()) {
                    s = studyGroup.get();
                    s.setStudentsCount(s.getStudentsCount() + 1);
                    person.setStudyGroup(s);
                    studyGroupRepository.save(s);
                }
                personRepository.save(person);
                return;
            } else {
                if (studyGroup.isPresent()) {
                    if (psg_current.getPersons().size() == 1) {
                        person.setStudyGroup(psg_current);
                        personRepository.save(person);
                        return;
                    }
                    if (p_current_in_db.getStudyGroup().getGroupAdmin() == person.getId()) {
                        psg_current.setGroupAdmin(psg_current.getPersons().get(psg_current.getPersons().size() - 1).getId());
                    }

                    psg_current.setStudentsCount(psg_current.getStudentsCount() - 1);
                    studyGroupRepository.save(psg_current);
                    StudyGroup s = studyGroup.get();
                    s.setStudentsCount(s.getStudentsCount() + 1);
                    person.setStudyGroup(s);
                    studyGroupRepository.save(s);
                    personRepository.save(person);
                    return;
                }
            }
        } else {
            return;
        }
        return;
    }


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
        Optional<StudyGroup> studyGroup = studyGroupRepository.findById((long) request.group_id());
        Optional<Person> personBD = personRepository.findById(id);
        if (personBD.isPresent()) {
            Person p_current_in_db = personBD.get();
            StudyGroup psg_current = p_current_in_db.getStudyGroup();
            if (psg_current == null) {
                StudyGroup s;
                if (studyGroup.isPresent()) {
                    s = studyGroup.get();
                    s.setStudentsCount(s.getStudentsCount() + 1);
                    person.setStudyGroup(s);
                    studyGroupRepository.save(s);
                }
                return personMapper.toDto(personRepository.save(person));
            } else {
                if (studyGroup.isPresent()) {
                    if (psg_current.getPersons().size() == 1) {
                        person.setStudyGroup(psg_current);
                        return personMapper.toDto(personRepository.save(person));
                    }
                    if (p_current_in_db.getStudyGroup().getGroupAdmin() == id) {
                        psg_current.setGroupAdmin(psg_current.getPersons().get(psg_current.getPersons().size() - 1).getId());
                    }

                    psg_current.setStudentsCount(psg_current.getStudentsCount() - 1);
                    studyGroupRepository.save(psg_current);
                    StudyGroup s = studyGroup.get();
                    s.setStudentsCount(s.getStudentsCount() + 1);
                    person.setStudyGroup(s);
                    studyGroupRepository.save(s);
                    return personMapper.toDto(personRepository.save(person));
                }
            }
        } else {
            return null;
        }
        return null;
    }

    public void deletePerson(int id) {
        Optional<Person> p = personRepository.findById(id);
        if (p.isPresent()) {
            Person pp = p.get();
            if (pp.getStudyGroup() == null) {
                personRepository.deleteById(id);
                return;
            }
            if (pp.getStudyGroup().getId() == id) {
                if (pp.getStudyGroup().getPersons().size() == 1) {
                    return;
                }
                pp.getStudyGroup().setGroupAdmin(pp.getStudyGroup().getPersons().get(pp.getStudyGroup().getPersons().size() - 1).getId());
                pp.getStudyGroup().setStudentsCount(pp.getStudyGroup().getStudentsCount() - 1);
                studyGroupRepository.save(pp.getStudyGroup());
                personRepository.deleteById(id);

            } else {
                pp.getStudyGroup().setStudentsCount(pp.getStudyGroup().getStudentsCount() - 1);
                personRepository.deleteById(id);
            }
        }
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


    public void deleteElementsFromGroup(Integer groupId) {
        personRepository.deleteAllByStudyGroupId(groupId);
    }
}

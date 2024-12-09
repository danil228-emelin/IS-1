package itmo.is.service.domain;

import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.dto.domain.request.CreateStudyGroupRequest;
import itmo.is.dto.domain.response.CountResponse;
import itmo.is.mapper.domain.PersonMapper;
import itmo.is.mapper.domain.StudyGroupMapper;
import itmo.is.model.domain.Person;
import itmo.is.model.domain.StudyGroup;
import itmo.is.repository.PersonRepository;
import itmo.is.repository.StudyGroupRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class GroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMapper studyGroupMapper;
    private final PersonMapper personMapper;
    private PersonRepository personRepository;

    @Autowired
    public GroupService(StudyGroupRepository studyGroupRepository, StudyGroupMapper studyGroupMapper, PersonRepository personRepository, PersonMapper personMapper) {
        this.studyGroupRepository = studyGroupRepository;
        this.studyGroupMapper = studyGroupMapper;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void addPersonToGroup(Integer groupId, Integer personId) {
        log.info("addPersonToGroup starts");
        // Validate the group and person existence
        StudyGroup studyGroup = studyGroupRepository.findById(Long.valueOf(groupId))
                .orElseThrow(() -> new RuntimeException("Study group not found"));

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));


        if (studyGroupRepository.existsStudyGroupByGroupAdminEquals(personId)) {
            log.info("Person is an admin with id "+personId);
            log.info("addPersonToGroup ends");
            return;

        }
        // Add the person to the group
        person.setStudyGroup(studyGroup);

        // Update the group with the new student count (optional)
        studyGroup.setStudentsCount(studyGroup.getStudentsCount() + 1);
        studyGroup.getPersons().add(person); // Add person to the group's list of students

        // Save both entities
        personRepository.save(person);
        studyGroupRepository.save(studyGroup);
        log.info("addPersonToGroup ends");
    }

    public StudyGroupDto createGroup(CreateStudyGroupRequest request) {
        log.info("createGroup started");
        StudyGroup newOne = studyGroupMapper.toEntity(request);
        var person = personRepository.findById(request.groupAdmin());
        if (person.isPresent()) {
            newOne.setGroupAdmin(person.get().getId());
            person.get().setStudyGroup(newOne);
            personMapper.toDto(personRepository.save(person.get()));
        }
        log.info("createGroup finished");
        return studyGroupMapper.toDto(studyGroupRepository.save(newOne));
    }

    public PersonDto findGroupAdminWithMinimalId() {
        Optional<Person> personOption = personRepository.findById(studyGroupRepository.findElementWithMinGroupId());
        return personMapper.toDto(personOption.get());
    }

    public Page<StudyGroupDto> findAllGroups(String name, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return studyGroupRepository.findAllByNameContaining(name, pageable).map(studyGroupMapper::toDto);
        }
        return studyGroupRepository.findAll(pageable).map(studyGroupMapper::toDto);
    }

    public StudyGroup findStudyGroupById(Long id) {
        return studyGroupRepository.findById(id)
                .orElse(null);
    }

    public CountResponse countGroupsByLessEqualAverageMark(Double averageMark) {
        return new CountResponse(studyGroupRepository.countStudyGroupsByAverageMarkLessThanEqual(averageMark));
    }

    public CountResponse countStudyGroupByGroupAdminGreaterThanEqual(Integer group_id) {
        return new CountResponse(studyGroupRepository.countStudyGroupByGroupAdminGreaterThanEqual(group_id));
    }

    public void deleteElementsFromGroup(Integer groupId) {
        personRepository.deleteAllByStudyGroupId(groupId);
    }
}

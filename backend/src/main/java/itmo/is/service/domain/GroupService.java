package itmo.is.service.domain;

import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.dto.domain.request.CreateStudyGroupRequest;
import itmo.is.mapper.domain.StudyGroupMapper;
import itmo.is.model.domain.Person;
import itmo.is.model.domain.StudyGroup;
import itmo.is.repository.PersonRepository;
import itmo.is.repository.StudyGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMapper studyGroupMapper;
    private PersonRepository personRepository;

    @Autowired
    public GroupService(StudyGroupRepository studyGroupRepository, StudyGroupMapper studyGroupMapper, PersonRepository personRepository) {
        this.studyGroupRepository = studyGroupRepository;
        this.studyGroupMapper = studyGroupMapper;
        this.personRepository = personRepository;
    }


    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void addPersonToGroup(Long groupId, Integer personId) {
        // Validate the group and person existence
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Study group not found"));

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        // Add the person to the group
        person.setStudyGroup(studyGroup);

        // Update the group with the new student count (optional)
        studyGroup.setStudentsCount(studyGroup.getStudentsCount() + 1);
        studyGroup.getPersons().add(person); // Add person to the group's list of students

        // Save both entities
        personRepository.save(person);
        studyGroupRepository.save(studyGroup);
    }

    public StudyGroupDto createGroup(CreateStudyGroupRequest request) {
        return studyGroupMapper.toDto(studyGroupRepository.save(studyGroupMapper.toEntity(request)));
    }

    public Page<StudyGroupDto> findAllGroups(String name, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return studyGroupRepository.findAllByNameContaining(name, pageable).map(studyGroupMapper::toDto);
        }
        return studyGroupRepository.findAll(pageable).map(studyGroupMapper::toDto);
    }
    public StudyGroup findStudyGroupById(Long id) {
        return studyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("StudyGroup not found with id: " + id));
    }
}

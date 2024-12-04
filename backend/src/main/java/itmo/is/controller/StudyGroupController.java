package itmo.is.controller;

import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.dto.domain.request.AddPersonToGroupRequest;
import itmo.is.dto.domain.request.CreateStudyGroupRequest;
import itmo.is.model.domain.StudyGroup;
import itmo.is.service.domain.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
public class StudyGroupController {
    @Autowired
    private GroupService studyGroupService;

    @PostMapping("/{groupId}/addPerson")
    public ResponseEntity<String> addPersonToGroup(@PathVariable Long groupId, @RequestBody AddPersonToGroupRequest request) {
        try {
            studyGroupService.addPersonToGroup(groupId, request.person_id());
            return ResponseEntity.ok("Person added to the group successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<StudyGroupDto> createGroup(@RequestBody CreateStudyGroupRequest request) {
        log.info("createGroup method started");
        try {
            StudyGroupDto newGroup = studyGroupService.createGroup(request);
            log.info("createGroup method finished successfully");
            return ResponseEntity.ok(newGroup);

        } catch (RuntimeException e) {
            log.info("createGroup method finished with error");
            log.info(e.getMessage());
            return ResponseEntity.status(400).body(null);
        }
    }
    @GetMapping("/getAllGroups")
    public ResponseEntity<Page<StudyGroupDto>> getAllGroups(
            @RequestParam(value = "name", required = false) String name,
            Pageable pageable
    ) {
        log.info("getAllGroups method started");
        Page<StudyGroupDto> people = studyGroupService.findAllGroups(name, pageable);
        log.info("getAllGroups method finished");
        return ResponseEntity.ok(people);
    }
}

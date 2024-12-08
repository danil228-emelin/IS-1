package itmo.is.controller;

import itmo.is.dto.domain.PersonDto;
import itmo.is.dto.domain.StudyGroupDto;
import itmo.is.dto.domain.request.AddPersonToGroupRequest;
import itmo.is.dto.domain.request.CreateStudyGroupRequest;
import itmo.is.dto.domain.response.CountResponse;
import itmo.is.service.domain.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class StudyGroupController {

    private GroupService studyGroupService;

    @Autowired
    public StudyGroupController(GroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }

    public void setStudyGroupService(GroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }

    @PostMapping("/addPerson")
    public ResponseEntity<String> addPersonToGroup(@RequestBody AddPersonToGroupRequest request) {
        try {
            log.info("addPersonToGroup methods start");
            studyGroupService.addPersonToGroup(request.group_id(), request.person_id());
            log.info("addPersonToGroup finish successfully");
            return ResponseEntity.ok("Person added to the group successfully");
        } catch (RuntimeException e) {
            log.info("addPersonToGroup finish with error");
            log.error(e.getMessage());
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


    @GetMapping("/getMinimalGroupId")
    public ResponseEntity<PersonDto> getElementWithMinimalGroupId() {
        log.info("getElementWithMinimalGroupId method started");
        PersonDto personDto = studyGroupService.findGroupAdminWithMinimalId();
        log.info("getElementWithMinimalGroupId method finished");
        return ResponseEntity.ok(personDto);
    }

    @GetMapping("/count-by-average-mark")

    public ResponseEntity<CountResponse> countGroupsByAverageMark(@RequestParam("averageMark") Double averageMark) {
        CountResponse response = studyGroupService.countGroupsByLessEqualAverageMark(averageMark);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count-by-admin-id")
    public ResponseEntity<CountResponse> countGroupsByAdminId(@RequestParam("groupId") Integer groupId) {
        CountResponse response = studyGroupService.countStudyGroupByGroupAdminGreaterThanEqual(groupId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-all")
    @PreAuthorize("@personSecurityService.isOwner(#groupId)")
    public ResponseEntity<String> deleteAll(@RequestParam("groupId") Integer groupId) {
        studyGroupService.deleteElementsFromGroup(groupId);
        return ResponseEntity.ok("delete people from group");
    }
}

package itmo.is.service.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import itmo.is.model.domain.Person;
import itmo.is.model.domain.StudyGroup;
import itmo.is.repository.PersonRepository;
import itmo.is.repository.StudyGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ImportService {

    private final ObjectMapper objectMapper;
    private final PersonRepository personRepository;
    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    public ImportService(ObjectMapper objectMapper, PersonRepository personRepository, StudyGroupRepository studyGroupRepository) {
        this.personRepository = personRepository;
        this.studyGroupRepository = studyGroupRepository;
        // Register custom deserializer for StudyGroup
        this.objectMapper = objectMapper;

        SimpleModule module = new SimpleModule();
        module.addDeserializer(StudyGroup.class, new StudyGroupDeserializer());
        this.objectMapper.registerModule(module);

    }

    /**
     * Imports a list of Person objects from a JSON file.
     *
     * @param file JSON file
     * @return List of Person objects
     * @throws IOException If an error occurs while reading or deserializing the file
     */
    public List<Person> importPersonsFromFile(File file) throws IOException {
        return objectMapper.readValue(file, new TypeReference<List<Person>>() {
        });
    }

    /**
     * Custom deserializer for StudyGroup objects.
     */
    public class StudyGroupDeserializer extends JsonDeserializer<StudyGroup> {
        @Override
        public StudyGroup deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
           log.info("deserialize for StudyGroup start");
            // Read the value as an integer (or another type as needed)

            Long groupId = jsonParser.getLongValue();

            return studyGroupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Study group not found with id"+ groupId));
        }
    }
}

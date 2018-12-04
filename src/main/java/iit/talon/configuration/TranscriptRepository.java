package iit.talon.configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import iit.talon.model.Transcript;
public interface TranscriptRepository extends MongoRepository<Transcript, String> {
}
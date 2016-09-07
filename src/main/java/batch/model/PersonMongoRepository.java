package batch.model;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Max Sveshnikov on 07.09.16.
 */

public interface PersonMongoRepository extends MongoRepository<Person, String> {
}


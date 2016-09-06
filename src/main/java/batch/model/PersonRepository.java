package batch.model;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Max Sveshnikov on 06.09.16.
 */
public interface PersonRepository extends CrudRepository<Person, Long> {
}

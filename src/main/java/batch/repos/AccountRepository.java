package batch.repos;

/**
 * Created by Max Sveshnikov on 08.09.16.
 */

import batch.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
package pl.hubertkuch.thesis.account.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hubertkuch.thesis.account.Account;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, String> {
    Optional<Account> findByNameOrEmailOrSlug(String name, String email, String slug);
}

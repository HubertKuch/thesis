package pl.hubertkuch.thesis.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hubertkuch.thesis.account.Account;
import pl.hubertkuch.thesis.application.Application;

import java.util.Optional;

@Repository
public interface AppRepo extends JpaRepository<Application, String> {
    Optional<Application> findByNameAndOwner(String name, Account owner);
}

package pl.hubertkuch.thesis.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.hubertkuch.thesis.application.Application;

@Repository
public interface ApplicationRepo extends JpaRepository<Application, String> {
}

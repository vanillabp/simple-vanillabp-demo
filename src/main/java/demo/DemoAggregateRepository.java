package demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoAggregateRepository extends JpaRepository<DemoAggregate, String> {

}

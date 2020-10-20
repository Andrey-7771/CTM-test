package crowd.lestcode.repo;

import crowd.lestcode.domain.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepo extends JpaRepository<Character, Long> {

    @Query("from Character e")
    List<Character> findByName(@Param("name") String name);

}

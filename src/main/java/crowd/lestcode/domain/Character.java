
package crowd.lestcode.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data

@Table(name = "character")
public class Character {

    @Id
    @GeneratedValue
    private Long id;
    private String characterName;
    private String characterNameOfComics;
}

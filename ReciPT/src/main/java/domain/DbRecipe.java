package domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    private String howToCook;
    private String thumbnailImage; //url
    private String recipeContext;
    private String recipeImage; //url

}

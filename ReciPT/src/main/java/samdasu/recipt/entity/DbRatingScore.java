package samdasu.recipt.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samdasu.recipt.global.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DbRatingScore extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "dbRating_id")
    private Long dbRatingId;

    private String dbRatingScore;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "db_id")
    private DbRecipe dbRecipe;

    //== 연관관계 편의 메서드 ==//
    public void changeDbRecipe(DbRecipe dbRecipe) {
        this.dbRecipe = dbRecipe;
        dbRecipe.getDbRatingScores().add(this);
    }

    public DbRatingScore(String dbRatingScore, DbRecipe dbRecipe) {
        this.dbRatingScore = dbRatingScore;
        changeDbRecipe(dbRecipe);
    }

    //==생성 메서드==//
    public static DbRatingScore createDbRatingScore(String dbRatingScore, DbRecipe dbRecipe) {
        return new DbRatingScore(dbRatingScore, dbRecipe);
    }
}

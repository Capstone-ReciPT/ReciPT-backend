package samdasu.recipt.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProfileEmbed is a Querydsl query type for ProfileEmbed
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QProfileEmbed extends BeanPath<ProfileEmbed> {

    private static final long serialVersionUID = 195723218L;

    public static final QProfileEmbed profileEmbed = new QProfileEmbed("profileEmbed");

    public final StringPath filename = createString("filename");

    public final ArrayPath<byte[], Byte> profileData = createArray("profileData", byte[].class);

    public final StringPath type = createString("type");

    public QProfileEmbed(String variable) {
        super(ProfileEmbed.class, forVariable(variable));
    }

    public QProfileEmbed(Path<? extends ProfileEmbed> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProfileEmbed(PathMetadata metadata) {
        super(ProfileEmbed.class, metadata);
    }

}


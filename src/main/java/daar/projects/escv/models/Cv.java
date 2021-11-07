package daar.projects.escv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "cvs")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cv
{
    @Id
    private String cvId;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String content;

    public Cv (String name, String content)
    {
        this.name = name;
        this.content = content;
    }
    public String getCvId() {
        return cvId;
    }
    public String getName() {
        return name;
    }

}

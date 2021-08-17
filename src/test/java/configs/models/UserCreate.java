package configs.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCreate {
    @JsonProperty("name")
    private String nameCreate;
    @JsonProperty("job")
    private String jobCreate;
}

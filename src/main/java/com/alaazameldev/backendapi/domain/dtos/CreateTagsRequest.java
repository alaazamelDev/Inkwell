package com.alaazameldev.backendapi.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Builder;

@Builder
public record CreateTagsRequest(

    @NotEmpty(message = "At least one tag name is required")
    @Size(max = 10, message = "Maximum {max} tags allowed")
    Set<
        @Size(min = 2, max = 30, message = "Tag name must be between {min} and {max} characters}")
        @Pattern(regexp = "^[\\w\\s-]+$")
            String
        > names
) {

}

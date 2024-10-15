package com.hepsi.emlak.todo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TodoRequest implements Serializable {
    @JsonProperty("title")
    @Schema(name = "title", example = "Foo")
    @NotNull
    String title;
    @Schema
    @JsonProperty("date")
    @NotNull
    LocalDate date;
    @JsonProperty("description")
    @Schema(name = "description", example = "Bar")
    String description;
    @JsonProperty("userId")
    @NotNull
    @NotBlank
    String userId;
    @JsonProperty("completed")
    Boolean completed;
}

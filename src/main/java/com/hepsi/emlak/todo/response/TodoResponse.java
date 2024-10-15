package com.hepsi.emlak.todo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TodoResponse {
    @JsonProperty("id")
    @NotBlank
    String id;
    @Schema(name = "title", example = "Foo")
    @JsonProperty("title")
    @NotBlank
    String title;
    @Schema
    @JsonProperty("date")
    @NotNull
    LocalDate date;
    @JsonProperty("description")
    @Schema(name = "description", example = "Bar")
    String description;
    @JsonProperty("completed")
    @NotNull
    @Builder.Default
    Boolean completed = false;
}

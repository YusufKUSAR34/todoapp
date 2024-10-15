package com.hepsi.emlak.todo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserRequest implements Serializable {
    @JsonProperty("userName")
    @Schema(name = "userName")
    @NotNull
    @NotBlank
    String userName;
    @JsonProperty("password")
    @Schema(name = "password")
    @NotNull
    @NotBlank
    String password;
}

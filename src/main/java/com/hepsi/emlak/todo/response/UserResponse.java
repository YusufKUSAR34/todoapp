package com.hepsi.emlak.todo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse implements Serializable {
    @JsonProperty("userId")
    String userId;
    @JsonProperty("userName")
    @Schema(name = "userName")
    @NotBlank
    String userName;
    @JsonProperty("password")
    @Schema(name = "password")
    @NotBlank
    String password;
}

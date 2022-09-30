package com.melita_task.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullNameDto {

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;
}
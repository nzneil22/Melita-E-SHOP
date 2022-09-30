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
public class InstallationAddressDto {

    @NotBlank
    private String island;

    @NotBlank
    private String town;

    @NotBlank
    private String street;

    @NotBlank
    private String building;
}

package com.melita_task.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class InstallationAddress implements Serializable {

    private static final long serialVersionUID = 485566624809270330L;

    @NotBlank
    private String island;

    @NotBlank
    private String town;

    @NotBlank
    private String street;

    @NotBlank
    private String building;
}

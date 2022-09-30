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
public class FullName implements Serializable {

    private static final long serialVersionUID = 5729326052027232453L;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;
}
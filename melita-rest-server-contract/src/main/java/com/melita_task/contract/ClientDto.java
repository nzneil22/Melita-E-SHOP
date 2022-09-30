package com.melita_task.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @Valid
    @NonNull
    private UUID id;

    @Valid
    private FullNameDto fullName;

    @Valid
    private InstallationAddressDto installationAddress;

    @NonNull
    private ClientStatus status;

    private List<OrderDto> orders;
}

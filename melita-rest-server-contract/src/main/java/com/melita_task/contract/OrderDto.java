package com.melita_task.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private UUID id;

    @NonNull
    private Integer serviceId;

    @NonNull
    private LobTypes lobType;

    @NonNull
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Date installationDateAndTime;

    @NonNull
    private OrderStatus status;

    @NonNull
    private UUID clientId;
}

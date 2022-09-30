package com.melita_task.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.melita_task.contract.LobTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersUpdate {

    private Integer serviceId;

    private LobTypes lobType;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Date installationDateAndTime;
}

package com.melita_task.api.mapper;

import com.melita_task.api.models.Client;
import com.melita_task.api.models.Order;
import com.melita_task.contract.ClientDto;
import com.melita_task.contract.ClientDtoRabbit;
import com.melita_task.contract.NewClientRequestDto;
import com.melita_task.contract.OrderDto;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomMapper extends ConfigurableMapper {

    @Override
    public void configure(MapperFactory factory) {
        super.configure(factory);
        factory.classMap(Client.class, ClientDto.class)
                .byDefault()
                .register();

        factory.classMap(NewClientRequestDto.class, Client.class)
                .byDefault()
                .register();

        factory.classMap(NewClientRequestDto.class, ClientDto.class)
                .byDefault()
                .register();

        factory.classMap(Order.class, OrderDto.class)
                .customize(new ma.glasnost.orika.CustomMapper<>() {
                    @Override
                    public void mapAtoB(Order order, OrderDto orderDto, MappingContext context) {
                        super.mapAtoB(order, orderDto, context);
                        orderDto.setClientId(order.getClient().getId());
                    }
                })
                .byDefault()
                .register();

        factory.classMap(Client.class, ClientDtoRabbit.class)
                .byDefault()
                .register();

    }
}

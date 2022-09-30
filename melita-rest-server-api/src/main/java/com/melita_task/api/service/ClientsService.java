package com.melita_task.api.service;

import com.melita_task.api.amqp.MessagePayload;
import com.melita_task.api.amqp.MessageProducer;
import com.melita_task.api.dao.ClientDao;
import com.melita_task.api.exceptions.*;
import com.melita_task.api.models.*;
import com.melita_task.contract.ClientDtoRabbit;
import com.melita_task.contract.ClientStatus;
import com.melita_task.contract.NewClientRequestDto;
import com.melita_task.contract.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ClientsService {

    private final MapperFacade mapper;
    private final ClientDao clientDao;
    private final ProductCatalogService productCatalogService;
    private final MessageProducer messageProducer;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Client registerClient(final NewClientRequestDto request) {
        Client client = clientDao.save(mapper.map(request, Client.class));
        messageProducer.sendMessage(
                MessagePayload.builder()
                        .client(mapper.map(client, ClientDtoRabbit.class))
                        .alteration("Register New Client")
                        .build());
        return client;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Optional<Client> findClient(final UUID id) {
        return clientDao.find(id, true);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Client updateClient(final UUID clientId,
                               final FullNameUpdate fullName,
                               final InstallationAddressUpdate installationAddress) {

        Client client = verifyClient(clientId);

        if (fullName != null) client.updateClient(fullName);
        if (installationAddress != null) client.updateClient(installationAddress);

        clientDao.save(client);
        messageProducer.sendMessage(
                MessagePayload.builder()
                        .client(mapper.map(client, ClientDtoRabbit.class))
                        .alteration("Update Client")
                        .build());

        return client;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Order> getClientOrders(final UUID clientId) {

        Client c = verifyClient(clientId);

        return c.getOrders();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Order addOrder(final UUID clientId, final Order ordDto) {

        Client client = verifyClient(clientId);

        if(!productCatalogService.isServiceIdValid(ordDto.getServiceId()))
            throw new InvalidServiceIdException();

        Order ord = new Order(client,
                ordDto.getServiceId(),
                ordDto.getLobType(),
                ordDto.getInstallationDateAndTime(),
                OrderStatus.CREATED);

        client.getOrders().add(ord);

        clientDao.save(client);

        return ord;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Order editOrder(final UUID clientId, final UUID orderId, final OrdersUpdate oUpdate) {

        Client client = verifyClient(clientId);

        if(oUpdate.getServiceId() != null && !productCatalogService.isServiceIdValid(oUpdate.getServiceId()))
            throw new InvalidServiceIdException();

        Order ord = client.getOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        if (ord.getStatus().equals(OrderStatus.SUBMITTED))
            throw new OrderSubmittedException();

        ord.updateOrder(oUpdate);

        clientDao.save(client);

        return ord;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String cancelOrder(final UUID clientId, final UUID orderId) {

        Client client = verifyClient(clientId);

        Order ord = client.getOrders()
                .stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);

        if (ord.getStatus().equals(OrderStatus.SUBMITTED))
            throw new OrderSubmittedException();

        client.getOrders().remove(ord);

        clientDao.save(client);

        return "Deleted Order with id: " + orderId;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Order> submitOrders(final UUID clientId) {

        Client client = verifyClient(clientId);

        client.getOrders().stream()
                .filter(o -> o.getStatus().equals(OrderStatus.CREATED))
                .forEach(o -> o.setStatus(OrderStatus.SUBMITTED));

        messageProducer.sendMessage(
                MessagePayload.builder()
                        .client(mapper.map(client, ClientDtoRabbit.class))
                        .alteration("Submit Orders")
                        .build());

        clientDao.save(client);

        Hibernate.initialize(client.getOrders());

        return client.getOrders();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Client clientStatus(final UUID clientId, final ClientStatus cStatus) {
        Client client = clientDao.find(clientId, false).orElseThrow(ClientNotFoundException::new);

        if (client.getStatus().equals(ClientStatus.INACTIVE)
                && cStatus.equals(ClientStatus.INACTIVE)) {
            throw new ClientAlreadyDeActivatedException();
        } else if (client.getStatus().equals(ClientStatus.ACTIVE)
                && cStatus.equals(ClientStatus.ACTIVE)) {
            throw new ClientAlreadyActivatedException();
        }


        client.setStatus(cStatus);
        clientDao.save(client);

        messageProducer.sendMessage(
                MessagePayload.builder()
                        .client(mapper.map(client, ClientDtoRabbit.class))
                        .alteration("Client Status Change")
                        .build());

        return client;
    }

    public Client verifyClient(final UUID clientId) {
        Client client = clientDao.find(clientId, false).orElseThrow(EntityNotFoundException::new);
        if (client.getStatus().equals(ClientStatus.INACTIVE)) throw new ClientInactiveException();
        return client;
    }

}

package com.melita_task.api.dao;

import com.melita_task.api.models.Client;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;

@Data
@Slf4j
@Service
@Profile("!in-memory")
@RequiredArgsConstructor
public class MySqlClientDao implements ClientDao {

    private final TransactionTemplate transactionTemplate;

    private final ClientRepository clientRepository;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Client save(Client client) {
        clientRepository.save(client);
        return client;
    }

    @Override
    public Optional<Client> find(UUID clientId, boolean initializeOrders) {

        Optional<Client> c = clientRepository.findById(clientId);

        if(initializeOrders && c.isPresent()) Hibernate.initialize(c.get().getOrders());

        return c;

    }



}

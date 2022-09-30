package com.melita_task.api.dao;

import com.melita_task.api.models.Client;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
@Profile("in-memory")
@NoArgsConstructor
public class InMemoryClientDao implements ClientDao {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    private final List<Client> clients = new ArrayList<>();

    @Override
    public Client save(Client client) {
        clients.add(client);
        return client;
    }

    @Override
    public Optional<Client> find(UUID id, boolean init) {
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

}


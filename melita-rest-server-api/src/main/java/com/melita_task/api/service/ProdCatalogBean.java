package com.melita_task.api.service;

import lombok.SneakyThrows;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProdCatalogBean {
    private final List<Integer> prodCatalog = new ArrayList<>();

    @SneakyThrows
    @Cacheable(value = "prodCatalog", key = "'catalog'")
    public List<Integer> getProductCatalog(){
        Thread.sleep(5000);
        return prodCatalog;
    }
    @CachePut(value = "prodCatalog", key = "'catalog'")
    public List<Integer> addProdCatalog(int servId){
        if(!prodCatalog.contains(servId)) prodCatalog.add(servId);
        return prodCatalog;
    }

    @CachePut(value = "prodCatalog", key = "'catalog'")
    public List<Integer> remProdCatalog(int servId){
        if(!prodCatalog.contains(servId))
            throw new EntityNotFoundException();
        prodCatalog.remove((Integer) servId);
        return prodCatalog;
    }
}

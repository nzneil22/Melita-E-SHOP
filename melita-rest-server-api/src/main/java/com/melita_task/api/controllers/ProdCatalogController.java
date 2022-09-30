package com.melita_task.api.controllers;

import com.melita_task.api.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/prodCatalog")
@RequiredArgsConstructor
public class ProdCatalogController {

    private final ProductCatalogService productCatalogService;

    @GetMapping
    public List<Integer> getProdCatalog() {
        log.info("Received request to get product catalog");
        return productCatalogService.getProductCatalog();
    }

    @PostMapping("/{id}")
    public List<Integer> addProdCatalog(@PathVariable @NotNull final Integer id) {
        log.info("Received request to add service {} to product catalog", id);
        return productCatalogService.addProdCatalog(id);
    }

    @DeleteMapping("/{id}")
    public List<Integer> deleteProdCatalog(@PathVariable @NotNull final Integer id) {
        log.info("Received request to remove service {} from product catalog", id);
        return productCatalogService.remProdCatalog(id);
    }

    @GetMapping("/{id}")
    public boolean isInProdCatalog(@PathVariable @NotNull final Integer id) {
        log.info("Received request to query service {} in product catalog", id);
        return productCatalogService.isServiceIdValid(id);
    }

}

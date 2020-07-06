package com.ms.CatalogueService.Repositories;

import com.ms.CatalogueService.Entities.Catalogue;
import org.springframework.data.repository.CrudRepository;

public interface CatalogueRepository extends CrudRepository<Catalogue,Integer> {
}

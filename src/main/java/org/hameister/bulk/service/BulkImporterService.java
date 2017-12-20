package org.hameister.bulk.service;

import org.hameister.bulk.data.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hameister on 18.12.17.
 */
@Service
public class BulkImporterService {

    private final EntityManager entityManager;

    public BulkImporterService(EntityManager emf) {
        Assert.notNull(emf, "EntityManager must not be null");
        this.entityManager = emf;
    }

    @Transactional
    public List<Item> bulkWithEntityManager(List<Item> items) {
            items.forEach(item -> entityManager.persist(item));
            return items;
    }
}

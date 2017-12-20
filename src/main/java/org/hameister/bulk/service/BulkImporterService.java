package org.hameister.bulk.service;

import org.hameister.bulk.data.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by hameister on 18.12.17.
 */
@Service
public class BulkImporterService {

    private EntityManagerFactory emf;

    @Autowired
    public BulkImporterService(EntityManagerFactory emf) {
        Assert.notNull(emf, "EntityManagerFactory must not be null");
        this.emf = emf;
    }

    public List<Item> bulkWithEntityManager(List<Item> items) {
            EntityManager entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            items.forEach(item -> entityManager.persist(item));
            entityManager.getTransaction().commit();
            entityManager.close();

            return items;
    }
}

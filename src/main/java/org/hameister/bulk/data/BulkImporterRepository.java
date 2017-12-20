package org.hameister.bulk.data;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by hameister on 19.12.17.
 */

@Repository
public class BulkImporterRepository extends SimpleJpaRepository<Item, String> {

    private EntityManager entityManager;
    public BulkImporterRepository(EntityManager entityManager) {
        super(Item.class, entityManager);
        this.entityManager=entityManager;
    }

    @Transactional
    public List<Item> save(List<Item> items) {
        items.forEach(item -> entityManager.persist(item));
        return items;
    }
}

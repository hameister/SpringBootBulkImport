package org.hameister.bulk.service;

import org.hameister.bulk.data.BulkImporterRepository;
import org.hameister.bulk.data.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by hameister on 01.12.17.
 */
@Service
public class ItemService {

    private BulkImporterRepository itemRepositoryCustom;

    @Autowired
    public ItemService(BulkImporterRepository itemRepositoryCustom) {
        Assert.notNull(itemRepositoryCustom, "Repo must not be null");
        this.itemRepositoryCustom = itemRepositoryCustom;
    }

    public List<Item> getItems() {
        return itemRepositoryCustom.findAll();
    }

    public List<Item> bulkImport(List<Item> items) {
        return itemRepositoryCustom.save(items);
    }
}

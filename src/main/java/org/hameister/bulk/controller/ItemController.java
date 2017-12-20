package org.hameister.bulk.controller;

import org.hameister.bulk.data.Item;
import org.hameister.bulk.service.BulkImporterService;
import org.hameister.bulk.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hameister on 02.12.17.
 */
@RestController
public class ItemController {

    private ItemService itemService;
    private  BulkImporterService bulkImporterService;

    public ItemController(ItemService itemService, BulkImporterService bulkImporterService) {
        Assert.notNull(itemService, "Service must not be null");
        this.itemService = itemService;
        this.bulkImporterService= bulkImporterService;
    }

    @PostMapping(value = "/items")
    public ResponseEntity<List<Item>> importItems(@RequestBody List<Item> items) {
        return new ResponseEntity<List<Item>>(itemService.bulkImport(items), HttpStatus.CREATED);
    }

    @GetMapping(value = "/items")
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<List<Item>>(itemService.getItems(), HttpStatus.OK);
    }
    @GetMapping(value = "/repositoryimport")
    public ResponseEntity<List<Item>> importWithBulkRepository() {
        return new ResponseEntity<List<Item>>(itemService.bulkImport(createItems()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/serviceimport")
    public ResponseEntity<List<Item>> importWithBulkService() {
        return new ResponseEntity<List<Item>>(bulkImporterService.bulkWithEntityManager(createItems()), HttpStatus.CREATED);
    }

    private List<Item> createItems() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            item.setId(UUID.randomUUID().toString());
            item.setLocation("Board");
            item.setDescription("Item " + i + "IT:" + date);
            items.add(item);
        }
        return items;
    }
}

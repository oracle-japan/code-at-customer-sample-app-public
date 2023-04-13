package com.oracle.jp.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.jp.backend.repository.Item;
import com.oracle.jp.backend.service.ItemService;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    /**
     * @param itemService
     */
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Get all items.
     * 
     * @return all items
     */
    @GetMapping(path = "/items")
    public List<Item> getItems() {
        return itemService.getItems();
    }

    /**
     * Get item by id.
     * 
     * @param id
     * @return item
     */
    @GetMapping(path = "/items/{id}")
    public Item getItemById(@PathVariable("id") Integer id) {
        return itemService.getItemById(id);
    }
}

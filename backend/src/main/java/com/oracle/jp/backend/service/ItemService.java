package com.oracle.jp.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oracle.jp.backend.exception.ItemNotFoundException;
import com.oracle.jp.backend.repository.Item;
import com.oracle.jp.backend.repository.ItemRepository;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;

    /**
     * @param itemRepository
     */
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Get all items.
     * 
     * @return all items.
     */
    public List<Item> getItems() {
        var items = itemRepository.findAll();
        if (items.isEmpty()) {
            logger.info("Item is NOT found.");
            throw new ItemNotFoundException("Item is NOT found.");
        } else {
            return items;
        }
    }

    /**
     * Get item by id.
     * 
     * @param id
     * @return item
     */
    public Item getItemById(Integer id) {
        var item = itemRepository.findById(id);
        if (item.isEmpty()) {
            logger.info(String.format("Item: %s is not found.", id));
            throw new ItemNotFoundException(String.format("Item: %s is not found.", id));
        } else {
            return item.get();
        }
    }
}

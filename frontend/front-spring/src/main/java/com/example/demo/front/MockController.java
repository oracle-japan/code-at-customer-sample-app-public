package com.example.demo.front;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mock")
public class MockController {

    private final Item[] items;

    public MockController(){
    	items = new Item[3];
    	items[0] = new Item(8, "Italian Antique Hand-Painted Porcelain Trinket Boxes", new BigDecimal(70), "cancelled");
    	items[1] = new Item(2, "Pool tables, 1'' Slate, Real Wood, NO MDF", new BigDecimal(1450), "available");
    	items[2] = new Item(3, "2 pack tennis rackets", new BigDecimal(50), "sold");
    }

    @CrossOrigin
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public Item[] getItems(HttpServletRequest httpRequest) {
        return items;
    }

    @CrossOrigin
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Item getItem(@PathVariable Integer id) {
        Optional<Item> item = Arrays.stream(items).filter(i -> i.getId().equals(id)).findFirst();
        if(item.isPresent()){
            return item.get();
        }else{
            throw new IdNotFoundException(String.format("No item was found: id=%d" , id));
        }
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)  
    public class IdNotFoundException extends RuntimeException   
    {  
        public IdNotFoundException(String message){  
            super(message);
        }
    }  

}
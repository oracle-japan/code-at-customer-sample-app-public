package com.example.demo.helidonfront;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/mock")
@ApplicationScoped
public class ItemsMockResource {

    private final Item[] items;

    public ItemsMockResource(){
        items = new Item[3];
        items[0] = new Item(8, "Italian Antique Hand-Painted Porcelain Trinket Boxes", new BigDecimal(70), "cancelled");
        items[1] = new Item(2, "Pool tables, 1'' Slate, Real Wood, NO MDF", new BigDecimal(1450), "available");
        items[2] = new Item(3, "2 pack tennis rackets", new BigDecimal(50), "sold");
    }

    @GET @Path("/items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {
        return Response.ok().entity(items).type(MediaType.APPLICATION_JSON).build();
    }


    @GET @Path("/items/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") Integer id) {
        Optional<Item> item = Arrays.stream(items).filter(i -> i.getId().equals(id)).findFirst();
        if(item.isPresent()){
            return Response.ok().entity(item.get()).type(MediaType.APPLICATION_JSON).build();
        }else{
            return Response.status(Status.NOT_FOUND)
                .entity(String.format("No item was found: id=%d" , id))
                .type(MediaType.TEXT_PLAIN).build();
        }
    }

    public class Item{
        private Integer id;
        private String name;
        private BigDecimal price;
        private String status;

        public Item(){}

        public Item(Integer id, String name, BigDecimal price, String status) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.status = status;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
package org.acme;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/hello")
public class GreetingResource {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/myentity/list")
    public List<MyEntity> listEntities() {
        List<MyEntity> entities = em.createQuery("from MyEntity", MyEntity.class).getResultList();
        return entities;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/myentity/add")
    public MyEntity addEntity() {
        MyEntity entity = new MyEntity();
        entity.field = "field-" + System.currentTimeMillis();
        em.persist(entity);
        return entity;
    }
}

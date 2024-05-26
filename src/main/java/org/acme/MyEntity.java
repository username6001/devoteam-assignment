package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Example JPA entity.
 *
 * To use it, get access to a JPA EntityManager via injection.
 *
 * {@code
 *     @Inject
 *     EntityManager em;
 *
 *     public void doSomething() {
 *         MyEntity entity1 = new MyEntity();
 *         entity1.field = "field-1";
 *         em.persist(entity1);
 *
 *         List<MyEntity> entities = em.createQuery("from MyEntity", MyEntity.class).getResultList();
 *     }
 * }
 */
@Entity
public class MyEntity {
    @Id
    @GeneratedValue
    public Long id;

    public String field;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyEntity myEntity)) return false;
        return Objects.equals(id, myEntity.id) && Objects.equals(field, myEntity.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, field);
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                ", field='" + field + '\'' +
                '}';
    }
}

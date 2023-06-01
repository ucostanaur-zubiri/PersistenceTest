package eus.hekuntza.zubiri.model.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HelloWorldJPATest {
  @Test
  public void storeLoadMessage() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ch02.ex01");

    try {
      EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();

      Message message = new Message();
      message.setText("Hello World!");

      em.persist(message);
      em.getTransaction().commit();

      // INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')
      em.getTransaction().begin();

      List<Message> messages = em.createQuery("select m from Message m").getResultList();
      // SELECT * from MESSAGE
      messages.get(messages.size() - 1).setText("Hello World from JPA!");
      em.getTransaction().commit();
      // UPDATE MESSAGE set TEXT = 'Hello World from JPA!' where ID = 1

      assertAll(() -> assertEquals(1, messages.size()),
          () -> assertEquals("Hello World from JPA!", messages.get(0).getText()));

      em.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      emf.close();
    }

  }
}

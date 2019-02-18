package com.gameserver.database.dao.character;

import com.gameserver.database.HibernateSessionFactory;
import com.gameserver.database.entity.character.Character;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CharacterDao {

    public void save(Character character) {

        Transaction transaction = null;

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            // start a transaction

            transaction = session.beginTransaction();

            // save the account object

            session.save(character);

            // commit transaction

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        }

    }

    public List<Character> getCharactersByAccount(int account_id)
    {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            return session.createQuery("from Character where account_id = :account_id", Character.class).setParameter("account_id", account_id).list();
        }
    }

    public Character getCharacterByName(String name)
    {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from Character where name = :name", Character.class).setParameter("name", name).list().stream().findFirst().orElse(null);
        }
    }

    public Character getCharacterById(int id)
    {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from Character where id = :id", Character.class).setParameter("id", id).list().stream().findFirst().orElse(null);
        }
    }
}

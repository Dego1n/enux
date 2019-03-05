package com.gameserver.database.dao.actor;

import com.gameserver.database.HibernateSessionFactory;
import com.gameserver.database.entity.actor.NPCActor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class NPCDao {

    public void save(NPCActor npc) {

        Transaction transaction = null;

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            // start a transaction

            transaction = session.beginTransaction();

            // save the account object

            session.save(npc);

            // commit transaction

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        }

    }

    public List<NPCActor> getAllNpcs()
    {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            return session.createQuery("from NPCActor", NPCActor.class).list();
        }
    }

    public NPCActor getNpcById(int id)
    {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from NPCActor where id = :id", NPCActor.class).setParameter("id", id).list().stream().findFirst().orElse(null);
        }
    }
}

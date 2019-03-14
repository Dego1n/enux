package com.gameserver.database.dao.spawn;

import com.gameserver.database.HibernateSessionFactory;
import com.gameserver.database.entity.spawn.Spawn;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SpawnDao {

    public void save(Spawn spawn) {

        Transaction transaction = null;

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            // start a transaction

            transaction = session.beginTransaction();

            // save the account object

            session.save(spawn);

            // commit transaction

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        }

    }

    public List<Spawn> getAllSpawns()
    {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            return session.createQuery("from Spawn", Spawn.class).list();
        }
    }

    public Spawn getFirstSpawnForActorId(int id)
    {
        try(Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.createQuery("from Spawn where actor_id = :id", Spawn.class).setParameter("id", id).list().stream().findFirst().orElse(null);
        }
    }
}

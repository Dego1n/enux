package com.gameserver.database;

import com.gameserver.config.Config;
import com.gameserver.database.entity.actor.Character;
import com.gameserver.database.entity.spawn.Spawn;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            try {

                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties

                Properties settings = new Properties();

                settings.put(Environment.DRIVER, Config.DATABASE_DRIVER);

                settings.put(Environment.URL, Config.DATABASE_URL);

                settings.put(Environment.USER, Config.DATABASE_USER);

                settings.put(Environment.PASS, Config.DATABASE_PASSWORD);

                settings.put(Environment.DIALECT, Config.DATABASE_DIALECT);

                settings.put(Environment.SHOW_SQL, Config.DATABASE_SHOW_SQL);

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, Config.DATABASE_HBM2DLL);


                configuration.setProperties(settings);

                //TODO: move this
                configuration.addAnnotatedClass(Character.class);
                configuration.addAnnotatedClass(Spawn.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()

                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        return sessionFactory;

    }
}

package com.gameserver.database;

import com.gameserver.config.Config;
import com.gameserver.database.entity.actor.Character;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class DBDatastore {
    private static Datastore _datastore = null;
    public static Datastore getInstance() {
        if(_datastore == null) {
            _datastore = Morphia.createDatastore(
                    MongoClients.create(Config.DATABASE_CONNECTION_STRING),
                    Config.DATABASE_NAME
            );
            _datastore.getMapper()
                    .mapPackageFromClass(Character.class);
            _datastore.ensureIndexes();
        }
        return _datastore;
    }

}

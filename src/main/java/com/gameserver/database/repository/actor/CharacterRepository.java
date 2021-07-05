package com.gameserver.database.repository.actor;

import com.gameserver.database.DBDatastore;
import com.gameserver.database.entity.actor.Character;
import dev.morphia.Datastore;
import dev.morphia.query.experimental.filters.Filters;

import java.util.List;

public class CharacterRepository {
    private final Datastore _dataStore;

    public CharacterRepository() {
        _dataStore = DBDatastore.getInstance();
    }

    public List<Character> getCharactersByAccount(String account_id)
    {
        return _dataStore.find(Character.class).filter(Filters.eq("account_id", account_id)).iterator().toList();
    }

    public Character getCharacterByName(String name)
    {
        return _dataStore.find(Character.class).filter(Filters.eq("name", name)).first();
    }

    public Character getCharacterById(String id)
    {
        return _dataStore.find(Character.class).filter(Filters.eq("id", id)).first();
    }
}

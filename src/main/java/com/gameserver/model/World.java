package com.gameserver.model;

import com.gameserver.model.actor.PlayableCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class World {

    private static final Logger log = LoggerFactory.getLogger(World.class);

    private static World _instance;

    public static World getInstance()
    {
        if(_instance == null)
            _instance = new World();

        return _instance;
    }

    private List<PlayableCharacter> players;

    private World()
    {
        log.info("Initializing world");
        players = new ArrayList<>();
    }

    public void addPlayerToWorld(PlayableCharacter player)
    {
        players.add(player);
    }

    public List<PlayableCharacter> getPlayersInRadius(PlayableCharacter character, float radius)
    {
        List<PlayableCharacter> actors = new ArrayList<>();

        for(PlayableCharacter player : players)
        {
            if(
                    Math.sqrt(
                        Math.pow((player.getLocationX() - character.getLocationX()),2) +
                        Math.pow((player.getLocationY() - character.getLocationY()),2) +
                        Math.pow((player.getLocationZ() - character.getLocationZ()),2)
                    ) <= radius
            )
            {
                actors.add(player);
            }
        }

        return actors;
    }

}

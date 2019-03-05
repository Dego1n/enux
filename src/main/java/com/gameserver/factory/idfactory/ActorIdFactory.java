package com.gameserver.factory.idfactory;

/*
 * @author Dego1n
 * Copyright (c) 02.03.2019
 */

public class ActorIdFactory {

    private static ActorIdFactory _instance;

    public static ActorIdFactory getInstance()
    {
        if(_instance == null)
            _instance = new ActorIdFactory();

        return _instance;
    }

    private int _nextIdValue;

    private ActorIdFactory()
    {
        _nextIdValue = 1;
    }

    public int getFreeId()
    {
        return _nextIdValue++;
    }
}

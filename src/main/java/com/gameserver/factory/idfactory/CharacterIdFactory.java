package com.gameserver.factory.idfactory;

/*
 * @author Dego1n
 * Copyright (c) 02.03.2019
 */

public class CharacterIdFactory {

    private static CharacterIdFactory _instance;

    public static CharacterIdFactory getInstance()
    {
        if(_instance == null)
            _instance = new CharacterIdFactory();

        return _instance;
    }

    private int _nextIdValue;

    private CharacterIdFactory()
    {
        _nextIdValue = 1;
    }

    public int getFreeId()
    {
        return _nextIdValue++;
    }
}

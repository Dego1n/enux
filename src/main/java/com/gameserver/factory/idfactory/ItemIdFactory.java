package com.gameserver.factory.idfactory;

public class ItemIdFactory {

    private static ItemIdFactory _instance;

    public static ItemIdFactory getInstance()
    {
        if(_instance == null)
            _instance = new ItemIdFactory();

        return _instance;
    }

    private int _nextIdValue;

    private ItemIdFactory()
    {
        _nextIdValue = 1;
    }

    public int getFreeId()
    {
        return _nextIdValue++;
    }

}

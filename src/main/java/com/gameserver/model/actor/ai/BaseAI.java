package com.gameserver.model.actor.ai;

public class BaseAI {

    AiState _state = AiState.IDLE;

    public void changeStatus(AiState state)
    {
        this._state = state;
    }

    public AiState GetStatus()
    {
        return _state;
    }
}

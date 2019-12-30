package com.gameserver.packet.client2game;

import com.gameserver.instance.DataEngine;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.packet.game2client.AcquireSkillList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestAcquireSkill extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(RequestAcquireSkill.class);

    private final ClientListenerThread clientListenerThread;

    public RequestAcquireSkill(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        int ability_id = readD();
        clientListenerThread.playableCharacter.acquireAbility(ability_id);
        clientListenerThread.playableCharacter.sendPacket(new AcquireSkillList(clientListenerThread.playableCharacter, DataEngine.getInstance().getAbilityTreeByClassId(clientListenerThread.playableCharacter.getCharacterClass().getValue())));
    }
}

/*
 * Author: Dego1n
 * 2.1.2020
 */

package com.gameserver.packet.client2game;

import com.gameserver.instance.DataEngine;
import com.gameserver.network.thread.ClientListenerThread;
import com.gameserver.packet.AbstractReceivablePacket;
import com.gameserver.template.quest.Quest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestTalk extends AbstractReceivablePacket {

    private static final Logger log = LoggerFactory.getLogger(QuestTalk.class);

    private final ClientListenerThread clientListenerThread;

    public QuestTalk(ClientListenerThread listenerThread, byte[] packet) {
        super(packet);
        clientListenerThread = listenerThread;
        handle();
    }

    private void handle() {
        int objectId = readD();
        int questId = readD();
        String ref = readS();
        Quest quest = DataEngine.getInstance().getQuestById(questId);
        if(quest != null)
        {
            quest.onQuestTalk(clientListenerThread.playableCharacter,ref,objectId);
        }
    }
}

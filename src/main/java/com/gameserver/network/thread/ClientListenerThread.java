package com.gameserver.network.thread;

import com.gameserver.database.dao.actor.CharacterDao;
import com.gameserver.database.entity.actor.Character;
import com.gameserver.model.World;
import com.gameserver.model.actor.PlayableCharacter;
import com.gameserver.packet.AbstractSendablePacket;
import com.gameserver.packet.ClientPackets;
import com.gameserver.packet.game2client.CharacterList;
import com.gameserver.packet.game2client.CharacterSelectedOk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ClientListenerThread {

    private static final Logger log = LoggerFactory.getLogger(ClientListenerThread.class);

    private final AsynchronousSocketChannel _socketChannel;

    private short protocolVersion;

    private boolean writeIsPending = false;

    private List<AbstractSendablePacket> packetBuffer;

    public PlayableCharacter playableCharacter;

    public ClientListenerThread(AsynchronousSocketChannel socketChannel)
    {
        _socketChannel = socketChannel;
        packetBuffer = new ArrayList<>();
    }

    public void sendPacket(AbstractSendablePacket packet)
    {
        if(writeIsPending)
            packetBuffer.add(packet);
        else {
            writeIsPending = true;
            _socketChannel.write(ByteBuffer.wrap(packet.prepareAndGetData()), this, new CompletionHandler<Integer, ClientListenerThread>() {
                @Override
                public void completed(Integer result, ClientListenerThread thread) {
                    thread.writeIsPending = false;
                    if(packetBuffer.size() > 0)
                    {
                        //TODO: пофиксил здесь важный баг, сделать тоже самое в треде с сервером авторизации
                        //TODO: и в самом сервере авторизации
                        AbstractSendablePacket packet = packetBuffer.get(0);
                        thread.sendPacket(packet);
                        packetBuffer.remove(packet);
                    }
                }

                @Override
                public void failed(Throwable exc, ClientListenerThread thread) {
                    closeConnection();
                    thread.writeIsPending = false;
                }
            });
        }
    }

    public void receivableStream()
    {
        try
        {
            while( _socketChannel.isOpen())
            {
                // Выделаем память 2 байта в байтбаффер для размера пакета
                ByteBuffer byteBuffer = ByteBuffer.allocate( 2 );

                // Читаем размер пакета
                _socketChannel.read( byteBuffer ).get( 3, TimeUnit.MINUTES );

                //Конвертим байтбаффер в массив байтов
                byte[] bytePacketSize =  byteBuffer.array();

                //Конвертим массив байтов в шорт и получаем длинну пакета
                short size = (short)(((bytePacketSize[1] & 0xFF) << 8) | (bytePacketSize[0] & 0xFF));

                //Выделяем память под пакет нужного размера - 2 байта (размер мы уже получили)
                byteBuffer = ByteBuffer.allocate(size - 2);

                //Читаем пакет
                _socketChannel.read(byteBuffer).get(20, TimeUnit.SECONDS);

                //Передаем пакет Хендлеру
                ClientPackets.HandlePacket(this,byteBuffer.array());
            }
        }
        catch (InterruptedException | ExecutionException e)
        {
            log.error(e.getMessage());
            closeConnection();
            return;
        } catch (TimeoutException e)
        {
            closeConnection();
            return;
        }

       closeConnection();
    }

    private void closeConnection()
    {
        if(playableCharacter != null)
        {
            World.getInstance().removePlayerFromWorld(playableCharacter);
            this.playableCharacter.setClientListenerThread(null);
            this.playableCharacter = null;
        }
        try {
            _socketChannel.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void receivedGameSessionKey(int gameSessionKey) {
        //TODO make stuff with checking gameSessionKey
        //TODO получить аккаунт_ид от сервера авторизации
        //TODO отправлять персонажей именно этого аккаунта

        CharacterDao characterDao = new CharacterDao();
        List<Character> characters = characterDao.getCharactersByAccount(1);
        sendPacket(new CharacterList(characters));
    }

    public void characterSelected(int characterId) {
        //TODO: проверять что персонаж принадлежит аккаунту
        //TODO: проверять что персонаж не забанен

        CharacterDao characterDao = new CharacterDao();
        Character character = characterDao.getCharacterById(characterId);

        if(character == null)
        {
            closeConnection();
            return;
        }
        this.playableCharacter = new PlayableCharacter(this,character);
        sendPacket(new CharacterSelectedOk(character));
    }
}

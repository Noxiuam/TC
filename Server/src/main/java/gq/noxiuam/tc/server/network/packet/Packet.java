package gq.noxiuam.tc.server.network.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gq.noxiuam.tc.server.network.PacketHandler;
import gq.noxiuam.tc.server.network.packet.impl.*;
import io.javalin.websocket.WsContext;

import java.io.IOException;

public abstract class Packet {

    public static final BiMap<Class<?>, Integer> REGISTRY = HashBiMap.create();

    static {
        REGISTRY.put(PacketSendChannel.class, 1);
        REGISTRY.put(PacketUpdateScreen.class, 2);
        REGISTRY.put(PacketChannelMessage.class, 3);
        REGISTRY.put(PacketChannelUpdate.class, 4);
        REGISTRY.put(PacketChannelSync.class, 5);
    }

    public abstract void write(WsContext webSocket, ByteBufWrapper buf) throws IOException;

    public abstract void read(WsContext webSocket, ByteBufWrapper buf) throws IOException;

    public abstract void process(WsContext webSocket, PacketHandler packetHandler) throws IOException;

}
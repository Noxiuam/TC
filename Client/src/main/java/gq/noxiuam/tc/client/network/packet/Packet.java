package gq.noxiuam.tc.client.network.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gq.noxiuam.tc.client.network.PacketManager;
import gq.noxiuam.tc.client.network.packet.impl.*;

import java.io.IOException;

public abstract class Packet {

    public static final BiMap<Object, Object> REGISTRY = HashBiMap.create();

    static {
        REGISTRY.put(PacketAddChannel.class, 1); // Named PacketSendChannel on server.
        REGISTRY.put(PacketUpdateScreen.class, 2);
        REGISTRY.put(PacketChannelMessage.class, 3);
        REGISTRY.put(PacketChannelUpdate.class, 4);
        REGISTRY.put(PacketChannelSync.class, 5);
    }

    public abstract void write(ByteBufWrapper buffer) throws IOException;

    public abstract void read(ByteBufWrapper buffer) throws IOException;

    public abstract void handle(PacketManager webSocket) throws IOException;

}

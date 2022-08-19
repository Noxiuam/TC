package gq.noxiuam.tc.server.network.packet.impl;

import gq.noxiuam.tc.server.network.PacketHandler;
import gq.noxiuam.tc.server.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.server.network.packet.Packet;
import io.javalin.websocket.WsContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
public class PacketUpdateScreen extends Packet {
    private String screenName;

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        buf.writeString(screenName);
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) {
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
    }
}

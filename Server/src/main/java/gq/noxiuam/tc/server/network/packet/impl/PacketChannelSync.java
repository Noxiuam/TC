package gq.noxiuam.tc.server.network.packet.impl;

import gq.noxiuam.tc.server.network.PacketHandler;
import gq.noxiuam.tc.server.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.server.network.packet.Packet;
import io.javalin.websocket.WsContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PacketChannelSync extends Packet {

    private int total;

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) {
        buf.writeInt(this.total);
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) {
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
    }

}

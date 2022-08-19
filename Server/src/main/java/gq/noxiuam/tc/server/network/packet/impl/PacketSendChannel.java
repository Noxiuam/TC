package gq.noxiuam.tc.server.network.packet.impl;

import gq.noxiuam.tc.server.network.PacketHandler;
import gq.noxiuam.tc.server.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.server.network.packet.Packet;
import io.javalin.websocket.WsContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacketSendChannel extends Packet {

    private String name;
    private int id;
    private int currentUsers;
    private int userLimit;

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        buf.writeString(this.name);
        buf.writeInt(this.id);
        buf.writeInt(this.currentUsers);
        buf.writeInt(this.userLimit);
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) {
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
    }

}

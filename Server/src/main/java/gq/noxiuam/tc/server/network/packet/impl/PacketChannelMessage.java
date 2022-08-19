package gq.noxiuam.tc.server.network.packet.impl;

import gq.noxiuam.tc.server.Server;
import gq.noxiuam.tc.server.data.Handshake;
import gq.noxiuam.tc.server.data.Profile;
import gq.noxiuam.tc.server.network.PacketHandler;
import gq.noxiuam.tc.server.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.server.network.packet.Packet;
import io.javalin.websocket.WsContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PacketChannelMessage extends Packet {

    private String chatMsg;

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        buf.writeString(chatMsg);
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        this.chatMsg = buf.readString(1000);
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
        Handshake handshake = webSocket.attribute("handshake");

        if (handshake != null) {
            Profile profile = Server.getInstance().getConnectedUsers().get(handshake.getUsername());
            profile.sendPacket(webSocket, new PacketChannelMessage(handshake.getUsername() + ": " + this.chatMsg));
        }
    }

}

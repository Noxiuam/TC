package gq.noxiuam.tc.server.network.packet.impl;

import gq.noxiuam.tc.server.Server;
import gq.noxiuam.tc.server.data.Handshake;
import gq.noxiuam.tc.server.data.Profile;
import gq.noxiuam.tc.server.network.PacketHandler;
import gq.noxiuam.tc.server.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.server.network.packet.Packet;
import gq.noxiuam.tc.server.network.channel.Channel;
import io.javalin.websocket.WsContext;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PacketChannelUpdate extends Packet {

    private int channelId;

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) {
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) {
        this.channelId = buf.readInt();
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
        Handshake handshake = webSocket.attribute("handshake");

        if (handshake != null) {
            Profile profile = Server.getInstance().getConnectedUsers().get(handshake.getUsername());
            Channel channel = Server.getInstance().getChannelManager().getChannelByID(this.channelId);

            if (channel.getUsers().contains(profile)) {
                Server.getInstance().getPacketHandler().sendPacket(webSocket, new PacketChannelMessage("From Server: Sorry! Someone already has that name, please reconnect with a different name."));
                webSocket.session.close();
                return;
            }

            if (this.channelId == 0) {
                profile.sendPacket(webSocket, new PacketUpdateScreen("mainmenu"));
                channel.getUsers().remove(profile);

                for (Profile user : channel.getUsers()) {
                    user.sendPacket(webSocket, new PacketChannelMessage("[LEAVE] '" + handshake.getUsername() + "'" + " has left."));
                }
                return;
            }

            channel.addUser(Server.getInstance().getConnectedUsers().get(handshake.getUsername()));
            for (Profile user : channel.getUsers()) {
                user.sendPacket(webSocket, new PacketChannelMessage("[JOIN] '" + profile.getUsername() + "' has joined."));
            }
            profile.sendPacket(webSocket, new PacketChannelMessage("To leave, type /leave"));
        }
    }

}

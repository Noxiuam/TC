package gq.noxiuam.tc.client.network.packet.impl;

import gq.noxiuam.tc.client.Client;
import gq.noxiuam.tc.client.network.Channel;
import gq.noxiuam.tc.client.network.PacketManager;
import gq.noxiuam.tc.client.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.client.network.packet.Packet;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor
public class PacketAddChannel extends Packet {

    private String name;
    private int id;
    private int currentUsers;
    private int userLimit;

    @Override
    public void write(ByteBufWrapper buffer) {
    }

    @Override
    public void read(ByteBufWrapper buffer) throws IOException {
        this.name = buffer.readString(512);
        this.id = buffer.readInt();
        this.currentUsers = buffer.readInt();
        this.userLimit = buffer.readInt();
    }

    @Override
    public void handle(PacketManager webSocket) {
        Client.getInstance().getChannels().add(
                new Channel(this.name, this.id, this.currentUsers, this.userLimit)
        );
    }

}

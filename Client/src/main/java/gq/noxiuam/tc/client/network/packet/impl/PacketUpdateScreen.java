package gq.noxiuam.tc.client.network.packet.impl;

import gq.noxiuam.tc.client.Client;
import gq.noxiuam.tc.client.network.PacketManager;
import gq.noxiuam.tc.client.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.client.network.packet.Packet;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Setter
@NoArgsConstructor
public class PacketUpdateScreen extends Packet {

    private String name;

    @Override
    public void write(ByteBufWrapper buffer) {
    }

    @Override
    public void read(ByteBufWrapper buffer) throws IOException {
        this.setName(buffer.readString(512));
    }

    @Override
    public void handle(PacketManager webSocket) {
        Client.getInstance().getMenuManager().getMenus().get(this.name).init();
    }

}

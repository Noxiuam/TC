package gq.noxiuam.tc.client.network.packet.impl;

import gq.noxiuam.tc.client.network.PacketManager;
import gq.noxiuam.tc.client.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.client.network.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
public class PacketChannelUpdate extends Packet {

    private int channelId;

    @Override
    public void write(ByteBufWrapper buffer) {
        buffer.writeInt(this.channelId);
    }

    @Override
    public void read(ByteBufWrapper ignored) {
    }

    @Override
    public void handle(PacketManager ignored) {
    }

}

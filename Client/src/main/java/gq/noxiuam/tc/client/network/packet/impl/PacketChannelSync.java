package gq.noxiuam.tc.client.network.packet.impl;

import gq.noxiuam.tc.client.network.PacketManager;
import gq.noxiuam.tc.client.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.client.network.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PacketChannelSync extends Packet {

    private int total;

    @Override
    public void write(ByteBufWrapper buffer) {
    }

    @Override
    public void read(ByteBufWrapper buffer) {
        this.total = buffer.readInt();
    }

    @Override
    public void handle(PacketManager webSocket) {
        webSocket.updateSync(this.total);
    }

}

package gq.noxiuam.tc.client.network.packet.impl;

import gq.noxiuam.tc.client.network.PacketManager;
import gq.noxiuam.tc.client.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.client.network.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PacketChannelMessage extends Packet {

    private String msgIn;
    private String msgOut;

    public PacketChannelMessage(String msgOut) {
        this.msgOut = msgOut;
    }

    @Override
    public void write(ByteBufWrapper buffer) {
        buffer.writeString(this.msgOut);
    }

    @Override
    public void read(ByteBufWrapper buffer) throws IOException {
        this.msgIn = buffer.readString(512);
    }

    @Override
    public void handle(PacketManager webSocket) {
        System.out.println(this.msgIn);
    }

}

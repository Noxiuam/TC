package gq.noxiuam.tc.server.network;

import gq.noxiuam.tc.server.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.server.network.packet.Packet;
import io.javalin.websocket.WsContext;
import io.netty.buffer.Unpooled;

import java.io.IOException;

public class PacketHandler {

    public void handle(WsContext wsContext, ByteBufWrapper buf) {
        int id = buf.readVarInt();
        Class<?> clazz = Packet.REGISTRY.inverse().get(id);

        if (clazz == null) return;

        try {
            Packet packet = (Packet) clazz.newInstance();
            packet.read(wsContext, buf);
            packet.process(wsContext, this);
        } catch (InstantiationException | IllegalAccessException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendPacket(WsContext context, Packet packet) {
        if (!Packet.REGISTRY.containsKey(packet.getClass())) return;

        if (context != null && context.session.isOpen()) {
            ByteBufWrapper wrapper = new ByteBufWrapper(Unpooled.buffer());
            wrapper.writeVarInt(Packet.REGISTRY.get(packet.getClass()));

            try {
                packet.write(context, wrapper);
                context.send(wrapper.nioBuffer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

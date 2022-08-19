package gq.noxiuam.tc.client.network;

import gq.noxiuam.tc.client.Client;
import gq.noxiuam.tc.client.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.client.network.packet.Packet;
import gq.noxiuam.tc.client.network.packet.impl.PacketChannelUpdate;
import gq.noxiuam.tc.client.ui.menu.impl.MainMenu;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;

public class PacketManager extends WebSocketClient {

    public PacketManager(URI uri, Map<String, String> headers) {
        super(uri, new Draft_6455(), headers, 0);
    }

    @SneakyThrows
    public void sendToServer(Packet packet) {
        if (!this.isOpen()) return;

        final ByteBufWrapper buf = new ByteBufWrapper(Unpooled.buffer());
        buf.writeVarInt((Integer) Packet.REGISTRY.get(packet.getClass()));
        packet.write(buf);
        this.send(buf.array());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
    }

    public void handle(ByteBufWrapper buf) {
        final int id = buf.readVarInt();

        final Class<?> packetClass = (Class<?>) Packet.REGISTRY.inverse().get(id);

        try {
            final Packet packet = packetClass == null ? null : (Packet) packetClass.newInstance();

            if (packet == null) return;
            packet.read(buf);
            packet.handle(this);

            if (id == 1 && Client.getInstance().getChannels().size() == Client.getInstance().getServerChannelAmount()) {
                new MainMenu().init();
            }

        } catch (Exception exception) {
            System.out.println("Error from: " + packetClass);
            exception.printStackTrace();
        }
    }

    public void handleLocationChange(int id) {
        this.sendToServer(new PacketChannelUpdate(id));
        System.out.println();
    }

    @Override
    public void send(String string) {
        if (!this.isOpen()) return;
        super.send(string);
    }

    public void updateSync(int amount) {
        Client.getInstance().setServerChannelAmount(amount);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        this.handle(new ByteBufWrapper(Unpooled.wrappedBuffer(bytes.array())));
    }

    @Override
    public void onMessage(String s) {
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("[Client (Close)]: " + s + "(" + i + ")");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("[Client (Websocket Error)] " + e.getMessage());
    }

}

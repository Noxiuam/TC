package gq.noxiuam.tc.server.data;

import gq.noxiuam.tc.server.Server;
import gq.noxiuam.tc.server.network.packet.Packet;
import io.javalin.websocket.WsContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Profile {

    private final String username;
    private WsContext webSocket;

    public void sendPacket(WsContext ws, Packet packet) {
        Server.getInstance().getPacketHandler().sendPacket(ws, packet);
    }

}

package gq.noxiuam.tc.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gq.noxiuam.tc.server.data.Handshake;
import gq.noxiuam.tc.server.data.Profile;
import gq.noxiuam.tc.server.network.PacketHandler;
import gq.noxiuam.tc.server.network.packet.ByteBufWrapper;
import gq.noxiuam.tc.server.network.packet.impl.PacketSendChannel;
import gq.noxiuam.tc.server.network.packet.impl.PacketChannelSync;
import gq.noxiuam.tc.server.network.channel.Channel;
import gq.noxiuam.tc.server.network.channel.ChannelManager;
import io.javalin.Javalin;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Server {

    @Getter private static Server instance;
    private final Javalin app;
    private final ChannelManager channelManager;
    private final PacketHandler packetHandler;
    public Map<String, Profile> connectedUsers = new HashMap<>();
    private UUID authKey = null;

    public Server(Javalin app) {
        instance = this;
        this.app = app;

        this.channelManager = new ChannelManager();
        this.packetHandler = new PacketHandler();
        this.loadChannels();

        this.app.ws("/connect", ws -> {
            ws.onConnect(context -> {
                Handshake handshake = new Handshake(context.headerMap());

                context.attribute("handshake", handshake);
                this.connectedUsers.put(handshake.getUsername(), new Profile(handshake.getUsername()));

                System.out.println("[User Connect] " + handshake.getUsername() + " has connected.");
                this.getPacketHandler().sendPacket(context, new PacketChannelSync(Server.getInstance().getChannelManager().getChannels().size()));

                if (Server.getInstance().getChannelManager().getChannels().size() > 0) {
                    for (Channel channel : Server.getInstance().getChannelManager().getChannels()) {
                        this.getPacketHandler().sendPacket(context, new PacketSendChannel(channel.getName(), channel.getId(), channel.getUsers().size(), channel.getLimit()));
                    }
                }
            });

            ws.onClose(context -> {
                Handshake handshake = context.attribute("handshake");
                Profile profile = this.connectedUsers.get(handshake.getUsername());

                for (Channel channel : this.getChannelManager().getChannels()) {
                    channel.getUsers().remove(profile);
                }

                System.out.println("[User Disconnect] " + handshake.getUsername() + " has disconnected.");
                this.connectedUsers.remove(handshake.getUsername());
            });

            ws.onBinaryMessage(context -> packetHandler.handle(context, new ByteBufWrapper(Unpooled.wrappedBuffer(context.data()))));
        });
    }

    @SneakyThrows
    private void loadChannels() {
        try {
            JsonObject config = new JsonParser().parse(new FileReader("config.json")).getAsJsonObject();

            Set<Map.Entry<String, JsonElement>> channels = config.getAsJsonObject("channels").entrySet();
            for (Map.Entry<String, JsonElement> channel : channels) {
                JsonObject channelObj = channel.getValue().getAsJsonObject();
                String name = channel.getKey();
                int limit = channelObj.get("limit").getAsInt();
                int id = channelObj.get("id").getAsInt();

                this.getChannelManager().getChannels().add(new Channel(name, id, limit));
                System.out.println("Loaded Channel: Name: " + name + " Id: " + id + " Limit: " + limit);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Configuration not found! Creating the default channel setup.");

            for (int i = 0; i < 5; i++) {
                this.getChannelManager().getChannels().add(new Channel("Channel " + (i + 1), (i + 1), 10 + i + 5));
                System.out.println("Created Channel: Name: Channel " + (i + 1) + ", ID: " + (i + 1) + ", Limit: " + 10 + i + 5);
            }
        }
    }

    public static void main(String[] args) {
        try {
            new Server(Javalin.create().start("0.0.0.0", Integer.parseInt(args[1])));
        } catch (ArrayIndexOutOfBoundsException exception) {
            new Server(Javalin.create().start("0.0.0.0", 8080));
        }
    }

}

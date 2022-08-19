package gq.noxiuam.tc.client;

import gq.noxiuam.tc.client.util.data.MessageData;
import gq.noxiuam.tc.client.network.Channel;
import gq.noxiuam.tc.client.network.PacketManager;
import gq.noxiuam.tc.client.ui.menu.MenuManager;
import gq.noxiuam.tc.client.util.thread.MessageQueueThread;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class Client {
    @Getter
    private static Client instance;

    public List<Channel> channels = new ArrayList<>();
    public List<MessageData> messages = new ArrayList<>();

    public Map<String, Long> keys = new HashMap<>();

    public static String username;
    public String address;
    public int serverChannelAmount;

    private PacketManager websocket;
    private MenuManager menuManager;

    public Client(String alias, String address) {
        instance = this;
        this.menuManager = new MenuManager();
        this.address = address;

        final HashMap<String, String> headers = new HashMap<>();
        headers.put("username", alias);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new MessageQueueThread(), 1, 1, TimeUnit.MILLISECONDS);

        try {
            (this.websocket = new PacketManager(new URI(address + "/connect"), headers)).connect();
            this.websocket.setConnectionLostTimeout(0);
        } catch (Exception ignored) {
        }
    }

    public static void main(String[] args) {
        try {
            new Client(args[1], args[2]);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.err.println("You did not specify a valid argument, setting the default values.");
            new Client(System.getProperty("user.name"), "ws://0.0.0.0:8080");
        }
    }
}

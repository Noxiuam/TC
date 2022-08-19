package gq.noxiuam.tc.server.network.channel;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChannelManager {

    public final List<Channel> channels = new ArrayList<>();

    public Channel getChannelByID(int id) {
        return this.channels.stream().filter(channel -> channel.getId() == id).findFirst().orElse(null);
    }

}
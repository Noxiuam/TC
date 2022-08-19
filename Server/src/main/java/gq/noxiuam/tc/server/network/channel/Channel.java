package gq.noxiuam.tc.server.network.channel;

import gq.noxiuam.tc.server.data.Profile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Channel {

    private List<Profile> users = new ArrayList<>();
    private String name;
    private int id;
    private int limit;

    public Channel(String name, int id, int limit) {
        this.name = name;
        this.id = id;
        this.limit = limit;
    }

    public void addUser(Profile user) {
        this.users.add(user);
    }

}

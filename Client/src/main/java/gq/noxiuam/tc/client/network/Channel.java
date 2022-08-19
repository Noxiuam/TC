package gq.noxiuam.tc.client.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Channel {

    private final String name;
    private int id;
    private int userCount;
    private int limit;

}

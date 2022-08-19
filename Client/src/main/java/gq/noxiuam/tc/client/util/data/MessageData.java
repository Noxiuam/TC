package gq.noxiuam.tc.client.util.data;

import lombok.*;

@Getter
@AllArgsConstructor
public class MessageData {

    private String message;
    @Setter private boolean sent;

}
package gq.noxiuam.tc.client.util.thread;

import gq.noxiuam.tc.client.util.data.MessageData;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class MessageQueueThread extends Thread {

    public List<MessageData> messages = new ArrayList<>();

    @Override @SneakyThrows
    public void run() {
        Thread.sleep(320L); // fast enough

        for (MessageData messageData : this.messages) {
            if (messageData.isSent()) continue;
            System.out.println(messageData.getMessage());
            messageData.setSent(true);
        }

    }
}

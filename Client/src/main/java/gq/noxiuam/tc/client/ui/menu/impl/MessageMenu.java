package gq.noxiuam.tc.client.ui.menu.impl;

import gq.noxiuam.tc.client.Client;
import gq.noxiuam.tc.client.network.packet.impl.PacketChannelMessage;
import gq.noxiuam.tc.client.network.packet.impl.PacketChannelUpdate;
import gq.noxiuam.tc.client.ui.AbstractMenu;

import java.util.Scanner;

public class MessageMenu extends AbstractMenu {
    @Override
    public void init() {
        Scanner messageListener = new Scanner(System.in);
        String msg = messageListener.nextLine();

        //System.out.println(msg);

        if (msg.equalsIgnoreCase("/leave")) {
            this.clearScreen();
            Client.getInstance().getWebsocket().sendToServer(new PacketChannelUpdate(0));
            return;
        }

        if (msg.length() > 0) {
            Client.getInstance().getWebsocket().sendToServer(new PacketChannelMessage(msg));
            this.reload();
        } else {
            System.out.println("You must have a valid message to send!");
            this.reload();
        }
    }
}

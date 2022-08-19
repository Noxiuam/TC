package gq.noxiuam.tc.client.ui.menu.impl;

import gq.noxiuam.tc.client.Client;
import gq.noxiuam.tc.client.network.Channel;
import gq.noxiuam.tc.client.ui.AbstractMenu;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.Scanner;

public class MainMenu extends AbstractMenu {
    @SneakyThrows
    @Override
    public void init() {
        this.render();

        Scanner sc = new Scanner(System.in);
        int selectedChannelId;

        try {
            selectedChannelId = Integer.parseInt(sc.nextLine());
            if (selectedChannelId == 0) selectedChannelId = Integer.parseInt("null");
        } catch (NumberFormatException ignored) {
            System.out.println();
            System.out.println("That channel is currently not available.");
            Thread.sleep(1000);
            this.clearScreen();
            this.reload();
            return;
        }

        for (Channel channel : Client.getInstance().getChannels()) {
            if (channel.getId() == selectedChannelId) {
                Client.getInstance().getWebsocket().handleLocationChange(selectedChannelId);
                this.clearScreen();
                new MessageMenu().init();
            }
        }
    }

    @Override
    public void render() {
        System.out.println("----------------------------------------");
        System.out.println("     Welcome to complete anonymity.");
        System.out.println();
        System.out.println("Time: " + new Date());
        System.out.println("----------------------------------------");
        System.out.println("\n--- Available Channels ---");
        System.out.println();
        try {
            int index = 1;
            if (Client.getInstance().getChannels().size() == 0) {
                System.out.println("There are currently no available channels.");
                return;
            }
            for (Channel channel : Client.getInstance().getChannels()) {
                System.out.println("[" + index + "] - " + channel.getName() + " (" + channel.getUserCount() + "/" + channel.getLimit() + ")");
                ++index;
            }

            System.out.println();
            System.out.println("Enter a Channel ID to enter: ");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}

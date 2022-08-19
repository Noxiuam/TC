package gq.noxiuam.tc.client.ui;

import lombok.SneakyThrows;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractMenu {

    abstract public void init();

    public void render() {
    }

    @SneakyThrows
    public void reload() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                init();
            }
        }, 500);
    }

    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}

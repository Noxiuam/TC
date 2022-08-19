package gq.noxiuam.tc.client.ui.menu;

import gq.noxiuam.tc.client.ui.AbstractMenu;
import gq.noxiuam.tc.client.ui.menu.impl.MainMenu;
import gq.noxiuam.tc.client.ui.menu.impl.MessageMenu;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class MenuManager {
    public HashMap<String, AbstractMenu> menus = new HashMap<>();

    public MenuManager() {
        this.menus.put("mainmenu", new MainMenu());
        this.menus.put("msgmenu", new MessageMenu());
    }
}

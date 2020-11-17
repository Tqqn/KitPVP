package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.Kits.KitsGUI;
import KitPvP.Tqqn.Utils.Color;
import KitPvP.Tqqn.Utils.Config;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class signListener implements Listener {

    private static Game game;

    public signListener(Game game) {
        signListener.game = game;
    }

    //Getting all the signlines from the config for the Arenasign

    public String sign0 = Color.translate(Config.getSignLine0());
    public String sign1 = Color.translate(Config.getSignLine1());
    public String sign2 = Color.translate(Config.getSignLine2());
    public String sign3 = Color.translate(Config.getSignLine3());



    //if a player clicks a sign that equals the first line of the arenasign, open the KITGUI to choose a kit.
    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.hasBlock() && event.getClickedBlock().getType().equals(Material.SIGN) || event.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                Player player = event.getPlayer();
                if (sign.getLine(0).equals(sign0)) {

                    //if the player IS NOT in the arena list -> open GUI, else dont open and send msg
                    if (!game.isArena(player)) {
                        KitsGUI gui = new KitsGUI();
                        player.openInventory(gui.getInv());
                    } else {
                        player.sendMessage(Color.translate("&cYou are in the arena."));
                    }
                }
            }
        }
    }

    //if player with the permission kitpvp.signplace places a sign with the signkey on the first line,
    // it changes to the custom Arenasign stated as in the Config.
    @EventHandler
    public void arenaSignPlace(SignChangeEvent event) {
        Player player = event.getPlayer();

        String msg = event.getLine(0);
        if (player.hasPermission("kitpvp.signplace")) {
            if (msg != null && msg.equalsIgnoreCase(Config.getSignKey())) {
                event.setLine(0,sign0);
                event.setLine(1,sign1);
                event.setLine(2,sign2);
                event.setLine(3,sign3);
            }
        }
    }
}

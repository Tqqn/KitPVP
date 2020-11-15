package KitPvP.Tqqn.Listeners;

import KitPvP.Tqqn.Utils.Color;
import KitPvP.Tqqn.Utils.Config;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ArenaSign implements Listener {
    public String sign0 = Color.translate(Config.getSignLine0());
    public String sign1 = Color.translate(Config.getSignLine1());
    public String sign2 = Color.translate(Config.getSignLine2());
    public String sign3 = Color.translate(Config.getSignLine3());

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.hasBlock() && event.getClickedBlock().getType().equals(Material.SIGN)) {
            Sign sign = (Sign) event.getClickedBlock().getState();
            Player player = event.getPlayer();
            if (sign.getLine(0).equals(sign0)) {
               player.teleport(Config.getArenaSpawn());
               player.sendMessage(Color.translate("&3You joined the arena!"));
            }
        }
    }

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

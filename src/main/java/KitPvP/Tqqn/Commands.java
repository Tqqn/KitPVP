package KitPvP.Tqqn;

import KitPvP.Tqqn.Utils.Color;
import KitPvP.Tqqn.Utils.Config;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("kp")
@Description("This command allows to use the kitpvp plugin.")

public class Commands extends BaseCommand {

    public Game game;

    public Commands(Game game) {
        this.game = game;
    }

    @HelpCommand
    @Private
    public void help(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @CommandPermission("kitpvp.setspawn")
    @Subcommand("setspawn")
    @Description("Set the lobbyspawn.")
    public void setspawn(CommandSender sender) {
            Player player = (Player) sender;
            Config.setLobbySpawn(player);
            sender.sendMessage(Color.translate("&aYou set the lobbyspawn to &3" + player.getLocation().getX() + " " + player.getLocation().getZ() + " " + player.getLocation().getY() + " &a!"));
        }

    @CommandPermission("kitpvp.setarenaspawn")
    @Subcommand("setarenaspawn")
    @Description("Set the arena spawn.")
    public void setArenaSpawn(CommandSender sender) {
        Player player = (Player) sender;
        Config.setArenaSpawn(player);
        sender.sendMessage(Color.translate("&aYou set the arenaspawn to &3" + player.getLocation().getX() + " " + player.getLocation().getZ() + " " + player.getLocation().getY() + " &a!"));
    }
    @CommandPermission("kitpvp.kit")
    @Subcommand("kit")
    @Description("Gives a kit.")
    public void kit(CommandSender sender) {
        Player player = (Player) sender;
        KitsGUI gui = new KitsGUI();
        player.openInventory(gui.getInv());
    }
}

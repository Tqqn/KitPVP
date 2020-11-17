package KitPvP.Tqqn.Commands;

import KitPvP.Tqqn.Game;
import KitPvP.Tqqn.Kits.KitsGUI;
import KitPvP.Tqqn.Utils.Color;
import KitPvP.Tqqn.Utils.Config;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


//Standard Plugin command
@CommandAlias("kp")
@Description("This command allows to use the kitpvp plugin.")

public class Commands extends BaseCommand {

    public Game game;

    public Commands(Game game) {
        this.game = game;
    }

    //kp help -> shows the existing commands
    @HelpCommand
    @Private
    public void help(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
    //kp setspawn -> sets the spawn of the location of the command sender
    //permission -> kitpvp.setspawn
    @CommandPermission("kitpvp.setspawn")
    @Subcommand("setspawn")
    @Description("Set the lobbyspawn.")
    public void setspawn(CommandSender sender) {
            Player player = (Player) sender;
            Config.setLobbySpawn(player);
            sender.sendMessage(Color.translate("&aYou set the lobbyspawn to &3" + player.getLocation().getX() + " " + player.getLocation().getZ() + " " + player.getLocation().getY() + " &a!"));
        }
    //kp setarenaspawn -> sets the arenaspawn of the location of the command sender
    //permission -> kitpvp.setarenaspawn
    @CommandPermission("kitpvp.setarenaspawn")
    @Subcommand("setarenaspawn")
    @Description("Set the arena spawn.")
    public void setArenaSpawn(CommandSender sender) {
        Player player = (Player) sender;
        Config.setArenaSpawn(player);
        sender.sendMessage(Color.translate("&aYou set the arenaspawn to &3" + player.getLocation().getX() + " " + player.getLocation().getZ() + " " + player.getLocation().getY() + " &a!"));
    }
    //kp kit -> opens the KIT GUI to choose a kit //For the players who don't want to walk to the sign xD
    @CommandPermission("kitpvp.kit")
    @Subcommand("kit")
    @Description("Gives a kit.")
    public void kit(CommandSender sender) {
        Player player = (Player) sender;

        //Checks if player is in the arena.
        if (game.isArena(player)) {
            player.sendMessage(Color.translate("&cYou are in the arena."));

            //if not opens kit selector
        } else {
            KitsGUI gui = new KitsGUI();
            player.openInventory(gui.getInv());
        }
    }
}

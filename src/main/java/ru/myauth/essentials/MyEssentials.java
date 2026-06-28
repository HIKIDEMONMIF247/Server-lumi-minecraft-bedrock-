package ru.myauth.essentials;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyEssentials extends PluginBase {

    private final Map<String, Location> homes = new HashMap<String, Location>();
    private final Random random = new Random();

    @Override
    public void onEnable() {
        getLogger().info(TextFormat.GREEN + "MyEssentials для Lumi успешно запущен!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TextFormat.RED + "Эту команду можно писать только в игре!");
            return true;
        }

        Player player = (Player) sender;
        String name = player.getName().toLowerCase();

        switch (command.getName().toLowerCase()) {
            case "rtp":
                int x = random.nextInt(2000) - 1000;
                int z = random.nextInt(2000) - 1000;
                int y = player.getLevel().getHighestBlockAt(x, z);

                Location rtpLoc = new Location(x, y + 1, z, player.getLevel());
                player.teleport(rtpLoc);
                player.sendMessage(TextFormat.GREEN + "Ты телепортирован на координаты: " + x + ", " + y + ", " + z);
                return true;

            case "sethome":
                homes.put(name, player.getLocation());
                player.sendMessage(TextFormat.GREEN + "Точка дома успешно установлена!");
                return true;

            case "home":
                if (homes.containsKey(name)) {
                    player.teleport(homes.get(name));
                    player.sendMessage(TextFormat.GREEN + "Ты телепортирован домой!");
                } else {
                    player.sendMessage(TextFormat.RED + "У тебя нет дома! Напиши /sethome");
                }
                return true;

            case "tpto":
                if (args.length < 1) {
                    player.sendMessage(TextFormat.RED + "Используй: /tpto <ник_друга>");
                    return true;
                }
                Player target = Server.getInstance().getPlayer(args[0]);
                if (target != null) {
                    player.teleport(target.getLocation());
                    player.sendMessage(TextFormat.GREEN + "Ты телепортировался к " + target.getName());
                } else {
                    player.sendMessage(TextFormat.RED + "Игрок не найден или не в сети!");
                }
                return true;
        }
        return false;
    }
}

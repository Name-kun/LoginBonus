package xyz.namekun.loginbonus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginBonusCommand implements CommandExecutor {

    private final ConfigManager configuration = new ConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("prefix"));

        if (command.getName().equalsIgnoreCase("loginbonus")) {
            if (!sender.hasPermission("loginbonus")) { //権限がない場合の処理
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("message.noPerm")));
            } else { //権限がある場合の処理
                if (args.length < 1) { //引数がない場合、ヘルプを表示
                    for (String help : ConfigManager.config.getStringList("message.help")) {
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', help));
                    }
                } else { //引数がある場合
                    if (args[0].equalsIgnoreCase("reload")) { //引数がreloadだった場合
                        configuration.createFiles();
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("message.reload")));
                    } else if (args[0].equalsIgnoreCase("delete")) { //引数がdeleteだった場合
                        configuration.playerFile.delete();
                        configuration.createFiles();
                        sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("message.resetList")));
                    } else { //正しい引数ではない場合
                        for (String help : ConfigManager.config.getStringList("message.help")) {
                            sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', help));
                        }
                    }
                }
            }
        }
        return true;
    }
}

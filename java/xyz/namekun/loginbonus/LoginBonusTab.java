package xyz.namekun.loginbonus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class LoginBonusTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tab = new ArrayList<String>();
        if (command.getName().equalsIgnoreCase("loginbonus")) {
            if (args.length == 1) {
                tab.add("reload");
                tab.add("delete");
            }
        }
        return tab;
    }
}


package xyz.namekun.loginbonus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class LoginBonusListener implements Listener {

    //愛すべきメソッド集
    private final LoginBonus plugin = LoginBonus.getPlugin(LoginBonus.class);
    private final ConfigManager configuration = new ConfigManager();
    private final File playerFile = new File(plugin.getDataFolder(), "players.yml");

    //プレイヤーが参加した時のイベントを設定
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        String prefix = ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("prefix"));
        Player p = e.getPlayer();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        //プレイヤーのUUIDがファイルに含まれていた場合の処理
        if (ConfigManager.playerConf.getStringList("playerList").contains(String.valueOf(p.getUniqueId()))) {
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("message.alreadyGiven")));
        } else { //そうでない場合の処理
            List<String> list = ConfigManager.playerConf.getStringList("playerList");
            list.add(String.valueOf(p.getUniqueId()));
            ConfigManager.playerConf.set("playerList", list);
            try { //プレイヤーのUUIDを保存する
                ConfigManager.playerConf.save(playerFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //コマンドをコンフィグから読み取る
            for (String commands : ConfigManager.config.getStringList("commands")) {
                Bukkit.dispatchCommand(console, commands.replaceAll("%player%", p.getName()));
            }
            p.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("message.obtainBonus")));
        }
    }
}

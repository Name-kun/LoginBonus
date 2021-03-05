package xyz.namekun.loginbonus;


import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class LoginBonus extends JavaPlugin {

    /*
    おっと、今このコードを読み始めましたね、でもそうはさせない。いや、そうはさせるんだけど、その前にひとつだけ。
    僕はこのコードの全てを理解しているかと言われれば何とも言えません、少し下見てみてください。コメントが\\ﾄｩｯﾄｩﾙﾄｩﾄｩﾙｯﾄｩｯﾄｩﾙ//してますね。
    そうです、僕の頭の構成成分は12割が水です。でも大丈夫、動けばいいんだ、知らんけど。
    このコメントを書きながらふと思ったのが、Kotlinってなんだか、マスコットキャラクターでもいそうですよね。
    Kotlin公式PRキャラクター「ことりん」ですよ、たぶん名前が「小鳥」っていう女の子なんでしょうね。まあJavaしか書けないのでどうでもいいんですけど。
    それでは、そろそろ皆さんのContributionを阻害しないよう、コメントもここらへんで控えておきましょう。ではまた後ほど。
     */

    private ConfigManager configuration;

    @Override
    public void onEnable() {
        loadConfig();
        //コマンドやらイベントの実行クラス変更
        getCommand("loginbonus").setExecutor(new LoginBonusCommand());
        getCommand("loginbonus").setTabCompleter(new LoginBonusTab());
        getServer().getPluginManager().registerEvents(new LoginBonusListener(), this);

        //惑星ループ
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                String prefix = ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("prefix"));

                //時間の表記をフォーマットする
                Date now = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

                //現在時刻がコンフィグと同じだった場合の処理
                if (simpleDateFormat.format(now).equals(ConfigManager.config.get("time"))) {
                    //ファイルが存在しない場合の処理
                    if (!configuration.playerFile.exists()) {
                        getLogger().info(prefix + ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("message.noFile")));
                    } else { //ファイルが存在する場合の処理(プレイヤーファイル削除->生成)
                        configuration.playerFile.delete();
                        configuration.createFiles();
                        getLogger().info(prefix + ChatColor.translateAlternateColorCodes('&', ConfigManager.config.getString("message.resetList")));
                    }
                }
            }
        };
        task.runTaskTimer(LoginBonus.this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //プラグインロード時のコンフィグ関係の動作
    public void loadConfig() {
        configuration = new ConfigManager();
        configuration.createFiles();
    }
}

package me.sl.XNPlug;

import lombok.Getter;
import me.sl.XNPlug.Functions.Afk.Afk;
import me.sl.XNPlug.Functions.Afk.AfkExpansion;
import me.sl.XNPlug.Functions.FlyCommand.FlyCommand;
import me.sl.XNPlug.Functions.GetIP.GetIP;
import me.sl.XNPlug.Functions.HealCommand.HealCommand;
import me.sl.XNPlug.Functions.LagCommand.LagCommand;
import me.sl.XNPlug.Functions.ListCommand.ListCommand;
import me.sl.XNPlug.Functions.SuicideCommand.SuicideCommand;
import me.sl.XNPlug.Functions.WhoCommand.WhoCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class XNPlug extends JavaPlugin {
    @Getter
    private static XNPlug instance;

    @Getter
    private static long enableTime;

    @Getter
    private static Afk afk;
    @Getter
    private static ListCommand listCommand;
    @Getter
    private static FlyCommand flyCommand;
    @Getter
    private static GetIP getIP;
    @Getter
    private static SuicideCommand suicideCommand;
    @Getter
    private static LagCommand lagCommand;
    @Getter
    private static WhoCommand whoCommand;
    @Getter
    private static HealCommand healCommand;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        getLogger().info("初始化 XNPlug...");

        instance = this;
        enableTime = System.currentTimeMillis();

        if (getConfig().getBoolean("modules.afk")) {
            afk = new Afk(this, getConfig().getLong("afk-threshold-seconds"));
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new AfkExpansion(afk).register();
                getLogger().info("注册 PlaceholderAPI 成功！");
            } else {
                getLogger().warning("未找到 PlaceholderAPI 插件，该功能将不可用！");
            }
            afk.start();
            getLogger().info("XNPlug.afk 已启动");
        }

        if (getConfig().getBoolean("modules.list")) {
            listCommand = new ListCommand(this);
            getLogger().info("XNPlug.list 已启动");
        }

        if (getConfig().getBoolean("modules.fly")) {
            flyCommand = new FlyCommand(this);
            getLogger().info("XNPlug.fly 已启动");
        }

        if (getConfig().getBoolean("modules.getip")) {
            getIP = new GetIP(this);
            getLogger().info("XNPlug.getip 已启动");
        }

        if (getConfig().getBoolean("modules.suicide")) {
            suicideCommand = new SuicideCommand(this);
            getLogger().info("XNPlug.suicide 已启动");
        }

        if (getConfig().getBoolean("modules.lag")) {
            lagCommand = new LagCommand(this);
            getLogger().info("XNPlug.lag 已启动");
        }

        if (getConfig().getBoolean("modules.who")) {
            whoCommand = new WhoCommand(this);
            getLogger().info("XNPlug.who 已启动");
        }

        if (getConfig().getBoolean("modules.heal")) {
            healCommand = new HealCommand(this);
            getLogger().info("XNPlug.heal 已启动");
        }

        getLogger().info("XNPlug 加载完成！");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("关闭 XNPlug...");
    }
}

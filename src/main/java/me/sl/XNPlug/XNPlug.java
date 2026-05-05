package me.sl.XNPlug;

import lombok.Getter;
import me.sl.XNPlug.Functions.Afk.Afk;
import me.sl.XNPlug.Functions.Afk.AfkExpansion;
import me.sl.XNPlug.Functions.FlyCommand.FlyCommand;
import me.sl.XNPlug.Functions.GetIP.GetIP;
import me.sl.XNPlug.Functions.ListCommand.ListCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class XNPlug extends JavaPlugin {
    @Getter
    private static XNPlug instance;

    @Getter
    private static Afk afk;
    @Getter
    private static ListCommand listCommand;
    @Getter
    private static FlyCommand flyCommand;
    @Getter
    private static GetIP getIP;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        getLogger().info("初始化 XNPlug...");

        instance = this;

        afk = new Afk(this, getConfig().getLong("afk-threshold-seconds", 300));
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new AfkExpansion(afk).register();
            getLogger().info("注册 PlaceholderAPI 成功！");
        } else {
            getLogger().warning("未找到 PlaceholderAPI 插件，该功能将不可用！");
        }
        afk.start();
        getLogger().info("XNPlug.afk 已启动");

        listCommand = new ListCommand(this);
        getLogger().info("XNPlug.list 已启动");

        flyCommand = new FlyCommand(this);
        getLogger().info("XNPlug.fly 已启动");

        getIP = new GetIP(this);
        getLogger().info("XNPlug.getip 已启动");

        getLogger().info("XNPlug 已启动");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("关闭 XNPlug...");
    }
}

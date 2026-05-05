package me.sl.XNPlug;

import lombok.Getter;
import me.sl.XNPlug.Functions.Afk.Afk;
import me.sl.XNPlug.Functions.Afk.AfkExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class XNPlug extends JavaPlugin {

    @Getter
    private static XNPlug instance;

    @Getter
    private static Afk afk;

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

        getLogger().info("XNPlug 已启动");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("关闭 XNPlug...");
    }
}

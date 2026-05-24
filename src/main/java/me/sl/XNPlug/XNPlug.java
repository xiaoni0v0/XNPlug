package me.sl.XNPlug;

import lombok.Getter;
import me.sl.XNPlug.Functions.GetIP.GetIP;
import org.bukkit.plugin.java.JavaPlugin;

public final class XNPlug extends JavaPlugin {
    @Getter
    private static XNPlug instance;

    @Getter
    private static long enableTime;

    @Getter
    private static GetIP getIP;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        getLogger().info("初始化 XNPlug...");

        instance = this;
        enableTime = System.currentTimeMillis();

        if (getConfig().getBoolean("modules.getip")) {
            getIP = new GetIP(this);
            getLogger().info("XNPlug.getip 已启动");
        }

        getLogger().info("XNPlug 加载完成！");
    }

    @Override
    public void onDisable() {
        instance = null;
        getLogger().info("关闭 XNPlug...");
    }
}

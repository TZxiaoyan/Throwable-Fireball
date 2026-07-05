package fun.demc.fireball;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static org.bukkit.Bukkit.getPluginCommand;

public class ThrowableFireball extends JavaPlugin {
    private static ThrowableFireball instance;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        instance = this;
        Objects.requireNonNull(getPluginCommand("tfb")).setExecutor(new YieldCommand());
        Bukkit.getPluginManager().registerEvents(new ThrowManager(), this);
        Lang.load(this);
        getLogger().info(Lang.getMessage("pluginEnabled"));
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info(Lang.getMessage("pluginDisabled"));
    }

    public boolean isEnabled_(){
        return config.getBoolean("isEnabled");
    }
    public static ThrowableFireball getInstance(){
        return instance;
    }
    public void setEnabled_(boolean is){
        config.set("isEnabled", is);
        saveConfig();
    }
    public FileConfiguration getConfig_(){
        return config;
    }
    public void  setYield(float y){
        config.set("yield", y);
        saveConfig();
    }
}

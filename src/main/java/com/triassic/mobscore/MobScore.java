package com.triassic.mobscore;

import com.triassic.mobscore.listeners.EntityDeathListener;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class MobScore extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        Configuration config = getConfig();

        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EntityDeathListener(config, logger), this);
    }

    public static Map<EntityType, Integer> loadScoreEntities(Configuration config, Logger logger) {
        Map<EntityType, Integer> validEntities = new HashMap<>();
        ConfigurationSection entitiesSection = config.getConfigurationSection("mobscore.entities");

        for (String key : entitiesSection.getKeys(false)) {
            try {
                EntityType entityType = EntityType.valueOf(key);
                int score = entitiesSection.getInt(key);
                validEntities.put(entityType, score);
            } catch (IllegalArgumentException e) {
                logger.warning("Invalid entity " + key + " in entity list.");
            }
        }

        return validEntities;
    }
}

package com.triassic.mobscore.listeners;

import com.booksaw.betterTeams.Team;
import com.triassic.mobscore.MobScore;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Map;
import java.util.logging.Logger;

public class EntityDeathListener implements Listener {
    private final Map<EntityType, Integer> scoreEntities;

    public EntityDeathListener(Configuration config, Logger logger) {
        this.scoreEntities = MobScore.loadScoreEntities(config, logger);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        EntityType type = entity.getType();

        if (scoreEntities.containsKey(type)) {
            Player player = entity.getKiller();
            Team team = Team.getTeam(player);

            if (team == null) {
                return;
            }

            team.setScore(team.getScore() + scoreEntities.get(type));
        }
    }
}

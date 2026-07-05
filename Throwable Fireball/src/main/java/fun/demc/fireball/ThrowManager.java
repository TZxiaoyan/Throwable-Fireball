package fun.demc.fireball;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jspecify.annotations.NonNull;

public class ThrowManager implements Listener {
    ThrowableFireball instance = ThrowableFireball.getInstance();
    @EventHandler
    private void onPlayerUse(@NonNull PlayerInteractEvent e) {
        if((e.getAction() == Action.RIGHT_CLICK_BLOCK ||  e.getAction() == Action.RIGHT_CLICK_AIR)
                && e.getItem().getType() == Material.FIRE_CHARGE
            && instance.isEnabled_() && !(e.getPlayer().getGameMode() == GameMode.SPECTATOR)) {
            e.setCancelled(true);

            Player p = e.getPlayer();
            Fireball fireball = p.launchProjectile(Fireball.class);
            if(!(e.getPlayer().getGameMode() == GameMode.CREATIVE)) {
                e.getItem().setAmount(e.getItem().getAmount() - 1);
            }
            fireball.setYield(instance.getConfig_().getInt("yield"));
        }
    }

}

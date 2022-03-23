package io.github.itsflicker.slimeproxyloader

import io.github.thebusybiscuit.slimefun4.api.events.ResearchUnlockEvent
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit

/**
 * @author wlys
 * @since 2022/3/6 13:05
 */
object Listener {

    @SubscribeEvent(EventPriority.MONITOR, ignoreCancelled = true)
    fun e(e: ResearchUnlockEvent) {
        e.player.getDataContainer()["researches.${e.research.id}"] = true
    }

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        submit(delay = 20) {
            PlayerProfile.get(e.player) {
                val map = e.player.getDataContainer().getConfigurationSection("researches")?.getKeys(false) ?: return@get
                Slimefun.getRegistry().researches.forEach { research ->
                    if (map.contains(research.id.toString()) && !it.hasUnlocked(research)) {
                        it.setResearched(research, true)
                    }
                }
            }
        }
    }
}
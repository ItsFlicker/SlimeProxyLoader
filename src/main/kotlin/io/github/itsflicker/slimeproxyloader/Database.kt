package io.github.itsflicker.slimeproxyloader

import io.github.itsflicker.slimeproxyloader.database.DatabaseSQL
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Schedule
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit

/**
 * @author wlys
 * @since 2022/3/6 14:09
 */
object Database {

    val database by lazy { DatabaseSQL() }

    @Schedule(delay = 100, period = 20 * 60 * 5, async = true)
    @Awake(LifeCycle.DISABLE)
    fun save() {
        onlinePlayers().forEach { database.push(it.cast()) }
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        submit(async = true) {
            database.push(e.player.uniqueId)
            database.release(e.player.uniqueId)
        }
    }

    @SubscribeEvent(ignoreCancelled = true)
    fun e(e: PlayerKickEvent) {
        submit(async = true) {
            database.push(e.player.uniqueId)
            database.release(e.player.uniqueId)
        }
    }
}

fun Player.getDataContainer() = Database.database.pull(this.uniqueId)
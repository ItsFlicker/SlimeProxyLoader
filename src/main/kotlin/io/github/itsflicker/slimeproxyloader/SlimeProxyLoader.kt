package io.github.itsflicker.slimeproxyloader

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon
import org.bukkit.plugin.java.JavaPlugin
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.disablePlugin
import taboolib.common.platform.function.warning
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin

object SlimeProxyLoader : Plugin(), SlimefunAddon {

    val plugin by lazy { BukkitPlugin.getInstance() }

    @Config
    lateinit var conf: Configuration

    override fun onLoad() {
        if (!conf.getBoolean("Initialized")) {
            warning("Please configure config.yml and set 'Initialized' to true, then reboot this plugin.")
            disablePlugin()
        }
    }

    override fun onEnable() {
        Database.database
    }

    override fun getBugTrackerURL(): String {
        return "https://github.com/ItsFlicker/SlimeProxyLoader"
    }

    override fun getJavaPlugin(): JavaPlugin {
        return plugin
    }
}
package io.github.itsflicker.slimeproxyloader

import org.bukkit.configuration.file.YamlConfiguration
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import java.io.File
import java.util.*

/**
 * @author wlys
 * @since 2022/3/6 13:05
 */
@CommandHeader("slimeproxyloader", ["slimepl", "spl"], permission = "slimeproxyloader.access")
object SPLCommand {

    @CommandBody
    val upload = subCommand {
        execute<ProxyCommandSender> { sender, _, _ ->
            val folder = File("data-storage/Slimefun/Players")
            folder.listFiles()?.forEach {
                val yaml = YamlConfiguration.loadConfiguration(it)
                val keys = yaml.getConfigurationSection("researches")?.getKeys(false) ?: return@forEach
                val uuid = UUID.fromString(it.nameWithoutExtension)
                val data = Database.database.pull(uuid)
                keys.forEach { key ->
                    data["researches.$key"] = true
                }
                Database.database.push(uuid)
                Database.database.release(uuid)
            }
            sender.sendMessage("Uploaded.")
        }
    }
}
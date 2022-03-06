package io.github.itsflicker.slimeproxyloader.database

import io.github.itsflicker.slimeproxyloader.SlimeProxyLoader
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import taboolib.module.database.ColumnOptionSQL
import taboolib.module.database.ColumnTypeSQL
import taboolib.module.database.Table
import taboolib.module.database.getHost
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class DatabaseSQL : Database() {

    val host = SlimeProxyLoader.conf.getHost("Database")

    val table = Table(SlimeProxyLoader.conf.getString("Database.table")!!, host) {
        add {
            name("user")
            type(ColumnTypeSQL.VARCHAR, 36) {
                options(ColumnOptionSQL.PRIMARY_KEY)
            }
        }
        add {
            name("data")
            type(ColumnTypeSQL.MEDIUMTEXT)
        }
    }

    val dataSource = host.createDataSource()
    val cache = ConcurrentHashMap<UUID, Configuration>()

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    override fun pull(uuid: UUID): ConfigurationSection {
        return cache.computeIfAbsent(uuid) {
            table.workspace(dataSource) {
                select { where { "user" eq uuid.toString() } }
            }.firstOrNull {
                Configuration.loadFromString(getString("data"))
            } ?: Configuration.empty(Type.YAML)
        }
    }

    override fun push(uuid: UUID) {
        val file = cache[uuid] ?: return
        if (table.workspace(dataSource) { select { where { "user" eq uuid.toString() } } }.find()) {
            table.workspace(dataSource) {
                update {
                    set("data", file.saveToString())
                    where {
                        "user" eq uuid.toString()
                    }
                }
            }.run()
        } else {
            table.workspace(dataSource) {
                insert("user", "data") {
                    value(uuid.toString(), file.saveToString())
                }
            }.run()
        }
    }

    override fun release(uuid: UUID) {
        cache.remove(uuid)
    }
}
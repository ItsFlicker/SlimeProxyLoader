package io.github.itsflicker.slimeproxyloader.database

import taboolib.library.configuration.ConfigurationSection
import java.util.*

/**
 * @author sky
 * @since 2020-08-14 14:38
 */
abstract class Database {

    abstract fun pull(uuid: UUID): ConfigurationSection

    abstract fun push(uuid: UUID)

    abstract fun release(uuid: UUID)

}
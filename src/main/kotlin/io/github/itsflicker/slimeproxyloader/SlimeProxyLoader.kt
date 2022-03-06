package io.github.itsflicker.slimeproxyloader

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

object SlimeProxyLoader : Plugin() {

    @Config
    lateinit var conf: Configuration

    override fun onEnable() {
        info("Successfully running ExamplePlugin!")
    }
}
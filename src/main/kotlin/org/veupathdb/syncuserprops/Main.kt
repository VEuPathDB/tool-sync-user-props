package org.veupathdb.syncuserprops;

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.Serializable
import java.nio.file.Path

@Serializable
data class Db(
    val user: String,
    val pass: String,
    val url: String
)

@Serializable
data class Config(
    val inputDb: Db,
    val outputDbs: List<Db>
)

data class User(
    val userId: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val organization: String,
    val signature: String
)

fun main(args: Array<String>) {
    val config = getConfig(args[0])
    println("Hello, world!")
}

fun getConfig(configFile: String) = Yaml.default.decodeFromString(
    Config.serializer(),
    Path.of(configFile).toFile().readText()
)


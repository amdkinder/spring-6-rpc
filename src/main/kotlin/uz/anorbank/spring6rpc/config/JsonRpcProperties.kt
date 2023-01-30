package uz.anorbank.spring6rpc.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jsonrpc", ignoreUnknownFields = false)
data class JsonRpcProperties(
    var serviceUri: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JsonRpcProperties

        if (!serviceUri.contentEquals(other.serviceUri)) return false

        return true
    }

    override fun hashCode(): Int {
        return serviceUri.contentHashCode()
    }
}
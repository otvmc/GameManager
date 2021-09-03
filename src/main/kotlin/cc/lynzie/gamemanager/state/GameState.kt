package cc.lynzie.gamemanager.state

import cc.lynzie.gamemanager.GameManager
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitTask
import java.time.Duration
import java.time.Instant

abstract class GameState(val gameManager: GameManager, val duration: Duration, val friendlyName: String) : Listener {
    var started = false
        private set
    var ended = false
        private set
    var frozen = false

    var listeners = mutableSetOf<Listener>()
    var tasks = mutableSetOf<BukkitTask>()

    lateinit var startTime: Instant
        private set

    fun start() {
        if (started || ended) return

        started = true
        startTime = Instant.now()
        registerListener(this)

        try {
            onStart()
        } catch (ex: Throwable) {
            throw StateException("Error while starting state ${javaClass.simpleName}", ex)
        }
    }

    fun tick() {
        if (!started || ended) return

        if (isAbleToEnd()) {
            end()
            return
        }

        try {
            onTick()
        } catch (ex: Throwable) {
            throw StateException("Error while ticking state ${javaClass.simpleName}", ex)
        }
    }

    fun end() {
        if (!started || ended) return

        ended = true

        listeners.forEach { HandlerList.unregisterAll() }
        listeners.clear()
        tasks.forEach { it.cancel() }
        tasks.clear()

        try {
            onEnd()
        } catch (ex: Throwable) {
            throw StateException("Error while ending state ${javaClass.simpleName}", ex)
        }
    }

    /**
     * Registers a [Listener], adding it to our [listeners]
     * set, as well as registering it on the server - registering
     * it this way makes it be unregistered during [end].
     */
    fun registerListener(listener: Listener) {
        listeners.add(listener)
        gameManager.plugin.server.pluginManager.registerEvents(listener, gameManager.plugin)
    }

    /**
     * Checks whether the [GameState] can end, ensuring
     * that the state is not [frozen], and that the time
     * between [startTime] and [Instant.now] either meets or
     * exceeds that of [duration].
     */
    fun isAbleToEnd(): Boolean {
        if (frozen) return false

        val timeElapsed = Duration.between(startTime, Instant.now())
        val remaining = timeElapsed.minus(duration)

        when {
            frozen -> return false
            remaining.isZero -> return true
            remaining.isNegative -> return true
            else -> return false
        }
    }

    abstract fun onStart()
    abstract fun onTick()
    abstract fun onEnd()
}
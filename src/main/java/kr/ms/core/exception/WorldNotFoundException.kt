package kr.ms.core.exception

class WorldNotFoundException(worldName: String): RuntimeException(
    "the world($worldName) is null"
)
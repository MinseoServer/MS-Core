package kr.ms.core.repo

interface Repository<K, V> {

    fun register(key: K, value: V)
    fun unregister(key: K)
    fun contains(key: K): Boolean
    fun get(key: K): V?

}
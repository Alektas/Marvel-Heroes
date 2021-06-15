package alektas.marvelheroes.core.data.mappers

interface Mapper<in From, out To> {
    fun map(from: From): To
}

interface DuplexMapper<From, To> {
    fun mapTo(from: From): To
    fun mapFrom(to: To): From
}

class ListMapper<From, To>(private val mapper: Mapper<From, To>): Mapper<List<From>, List<To>> {
    override fun map(from: List<From>): List<To> = from.map { mapper.map(it) }
}

class DuplexListMapper<From, To>(private val mapper: DuplexMapper<From, To>): DuplexMapper<List<From>, List<To>> {
    override fun mapTo(from: List<From>) = from.map { mapper.mapTo(it) }
    override fun mapFrom(to: List<To>) = to.map { mapper.mapFrom(it) }
}
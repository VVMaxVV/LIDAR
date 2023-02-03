package model

internal class ChunkHashMap : HashMap<Chunk, MutableList<Line>>() {
    override fun get(key: Chunk): MutableList<Line>? {
        if (super.get(key) == null) super.put(key, mutableListOf())
        return super.get(key)
    }
}

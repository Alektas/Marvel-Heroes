package alektas.marvelheroes.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class HeroesPagingOffsetEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "query")
    val query: String,
    @ColumnInfo(name = "next_offset")
    val nextOffset: Int?
)
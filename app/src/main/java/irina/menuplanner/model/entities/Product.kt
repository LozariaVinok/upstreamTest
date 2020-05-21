package irina.menuplanner.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Long = 0,
    @ColumnInfo var name: String = ""
)


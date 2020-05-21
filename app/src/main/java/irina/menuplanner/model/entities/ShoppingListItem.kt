package irina.menuplanner.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true) val shoppingListItemId: Int = 0,
    var productItemId: Long = 0,
    @ColumnInfo var isChecked: Boolean = false

)
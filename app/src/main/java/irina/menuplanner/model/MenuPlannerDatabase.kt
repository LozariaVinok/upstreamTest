package irina.menuplanner.model

import androidx.room.Database
import androidx.room.RoomDatabase
import irina.menuplanner.model.entities.Product
import irina.menuplanner.model.entities.ShoppingListItem

@Database(entities = [ShoppingListItem::class, Product::class], version = 1, exportSchema = false)
abstract class MenuPlannerDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun productDao(): ProductDao
}

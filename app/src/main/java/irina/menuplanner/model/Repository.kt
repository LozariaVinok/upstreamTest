package irina.menuplanner.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import irina.menuplanner.model.entities.Product
import irina.menuplanner.model.entities.ShoppingListItem
import timber.log.Timber

class Repository(context: Context) {


    private val db = Room.databaseBuilder(
        context,
        MenuPlannerDatabase::class.java, "menu-planner-database"
    ).build()

    fun getShoppingList(): LiveData<List<ShoppingListProductItem>> {
        return db.shoppingListDao().getShoppingList()
    }

    suspend fun addShoppingListItem() {
        val product = Product()
        val id = db.productDao().insert(product)
        val item =
            ShoppingListItem(productItemId = id.toInt())
        db.shoppingListDao().insertItem(item)
        Timber.i("Added new shopping list item")
    }

    suspend fun changeCheckedInShoppingList(shoppingListItemId: Int, checked: Boolean) {
        db.shoppingListDao()
            .updateItem(shoppingListItemId, checked)
        Timber.i("Shopping list item updated") //}
    }

    suspend fun updateProductName(productId: Int, name: String) {
        db.productDao().updateName(productId, name)
        Timber.i("Product name updated")
    }

    suspend fun deleteShoppingListItem(id: Int) {
        db.shoppingListDao().deleteItem(id)
        Timber.i("Shopping list item deleted")
    }
}
package irina.menuplanner.model

import androidx.lifecycle.LiveData
import irina.menuplanner.model.entities.Product
import irina.menuplanner.model.entities.ShoppingListItem
import timber.log.Timber

class Repository(private val db: MenuPlannerDatabase) : IRepository {

    override fun getShoppingList(): LiveData<List<ShoppingListProductItem>> {
        return db.shoppingListDao().getShoppingList()
    }

    override suspend fun addShoppingListItem(): Long {
        val product = Product()
        val id = db.productDao().insert(product)
        val item =
            ShoppingListItem(productItemId = id)
        val itemId = db.shoppingListDao().insertItem(item)
        Timber.i("Added new shopping list item")
        return itemId
    }

    override suspend fun changeCheckedInShoppingList(shoppingListItemId: Long, checked: Boolean) {
        db.shoppingListDao()
            .updateItem(shoppingListItemId, checked)
        Timber.i("Shopping list item updated") //}
    }

    override suspend fun updateProductName(productId: Long, name: String) {
        db.productDao().updateName(productId, name)
        Timber.i("Product name updated")
    }

    override suspend fun deleteShoppingListItem(id: Long) {
        db.shoppingListDao().deleteItem(id)
        Timber.i("Shopping list item deleted")
    }

    override fun getItem(id: Long): ShoppingListItem {
        return db.shoppingListDao().getItem(id)
    }
}
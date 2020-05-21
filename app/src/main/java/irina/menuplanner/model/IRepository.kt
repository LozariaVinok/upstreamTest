package irina.menuplanner.model

import androidx.lifecycle.LiveData
import irina.menuplanner.model.entities.ShoppingListItem

interface IRepository {
    fun getShoppingList(): LiveData<List<ShoppingListProductItem>>

    suspend fun addShoppingListItem(): Long

    suspend fun changeCheckedInShoppingList(shoppingListItemId: Long, checked: Boolean)

    suspend fun updateProductName(productId: Long, name: String)

    suspend fun deleteShoppingListItem(id: Long)
    fun getItem(id: Long): ShoppingListItem
}
package irina.menuplanner.model


data class ShoppingListProductItem(
    val shoppingListItemId: Long,
    val productId: Long,
    var isChecked: Boolean,
    var name: String
)
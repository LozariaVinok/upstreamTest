package irina.menuplanner.model


data class ShoppingListProductItem(
    val shoppingListItemId: Int,
    val productId: Int,
    var isChecked: Boolean,
    var name: String
)
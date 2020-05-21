package irina.menuplanner.ui.shoppinglist

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import irina.menuplanner.BR
import irina.menuplanner.model.IRepository
import irina.menuplanner.model.ShoppingListProductItem
import irina.menuplanner.ui.base.ObservableViewModel
import kotlinx.coroutines.launch


class ShoppingListViewModel(private val repository: IRepository) : ObservableViewModel() {
    val shoppingList: LiveData<List<ShoppingListProductItem>> = repository.getShoppingList()

    @Bindable
    var selectedItemId: Long = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemId)
        }

    @Bindable
    var selectedItemProductId: Long = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemProductId)
        }

    @Bindable
    var selectedItemName = ""
        set(value) {
            field = value
            viewModelScope.launch {
                repository.updateProductName(selectedItemProductId, value)
            }
            notifyPropertyChanged(BR.selectedItemName)
        }


    fun onDeleteButtonClicked(id: Long) = viewModelScope.launch {
        repository.deleteShoppingListItem(id)
    }

    fun onNewItemClicked() = viewModelScope.launch {
        val id = repository.addShoppingListItem()
    }

    fun onItemClicked(
        shoppingListItemId: Long,
        shoppingListProductName: String,
        shoppingListItemProductId: Long
    ) {
        selectedItemId = shoppingListItemId
        selectedItemProductId = shoppingListItemProductId
        selectedItemName = shoppingListProductName
    }

    fun onItemChecked(shoppingListItemId: Long, checked: Boolean) = viewModelScope.launch {
        repository.changeCheckedInShoppingList(shoppingListItemId, checked)
    }

}
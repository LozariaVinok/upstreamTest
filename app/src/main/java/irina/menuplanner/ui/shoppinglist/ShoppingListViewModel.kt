package irina.menuplanner.ui.shoppinglist

import androidx.databinding.Bindable
import androidx.lifecycle.*
import irina.menuplanner.BR
import irina.menuplanner.model.Repository
import irina.menuplanner.model.ShoppingListProductItem
import irina.menuplanner.ui.base.ObservableViewModel
import kotlinx.coroutines.launch


class ShoppingListViewModel(private val repository: Repository) : ObservableViewModel() {
    val shoppingList: LiveData<List<ShoppingListProductItem>> = repository.getShoppingList()

    @Bindable
    var selectedItemId = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.selectedItemId)
        }

    @Bindable
    private var selectedItemProductId = -1
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


    fun onDeleteButtonClicked(id: Int) = viewModelScope.launch {
        repository.deleteShoppingListItem(id)
    }

    fun onNewItemClicked() = viewModelScope.launch {
        repository.addShoppingListItem()
    }

    fun onItemClicked(
        shoppingListItemId: Int,
        shoppingListProductName: String,
        shoppingListItemProductId: Int
    ) {
        selectedItemId = shoppingListItemId
        selectedItemProductId = shoppingListItemProductId
        selectedItemName = shoppingListProductName
    }

    fun onItemChecked(shoppingListItemId: Int, checked: Boolean) = viewModelScope.launch {
        repository.changeCheckedInShoppingList(shoppingListItemId, checked)
    }

}
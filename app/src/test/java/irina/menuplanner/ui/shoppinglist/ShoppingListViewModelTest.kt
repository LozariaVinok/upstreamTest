package irina.menuplanner.ui.shoppinglist

import irina.menuplanner.model.IRepository
import irina.menuplanner.ui.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit


class ShoppingListViewModelTest {
    private lateinit var shoppingListViewModel: ShoppingListViewModel

    @Mock
    private lateinit var repository: IRepository

    @get:Rule
    var mockitoRule = MockitoJUnit.rule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun initViewModel() {
        shoppingListViewModel = ShoppingListViewModel(repository)
    }

    @Test
    fun onDeleteButtonClicked() = runBlocking {
        shoppingListViewModel.onDeleteButtonClicked(1).join()

        Mockito.verify(repository).deleteShoppingListItem(1)
    }

    @Test
    fun onNewItemClicked() = runBlocking {
        Mockito.`when`(repository.addShoppingListItem()).thenReturn(1)
        shoppingListViewModel.onNewItemClicked().join()
        Mockito.verify(repository).addShoppingListItem()
        print(0)
    }

    @Test
    fun onItemClicked() {
        shoppingListViewModel.onItemClicked(1, "Test", 2)
        Assert.assertEquals(1, shoppingListViewModel.selectedItemId)
        Assert.assertEquals("Test", shoppingListViewModel.selectedItemName)
        Assert.assertEquals(2, shoppingListViewModel.selectedItemProductId)

    }

    @Test
    fun onItemChecked() = runBlocking {
        shoppingListViewModel.onItemChecked(1, true).join()
        Mockito.verify(repository).changeCheckedInShoppingList(1, true)
    }
}
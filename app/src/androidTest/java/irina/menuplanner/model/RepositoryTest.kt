package irina.menuplanner.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepositoryTest {
    private lateinit var repository: Repository
    private lateinit var database: MenuPlannerDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createRepository() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, MenuPlannerDatabase::class.java).build()
        repository = Repository(database)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getShoppingList() = runBlocking {
        assertNotNull(repository.getShoppingList())
    }

    @Test
    fun addShoppingListItem() = runBlocking {
        repository.addShoppingListItem()
        val shoppingList = repository.getShoppingList()
        shoppingList.observeForever {
            assertEquals(1, it.size)
        }
    }

    @Test
    fun changeCheckedInShoppingList() = runBlocking {
        val id = repository.addShoppingListItem()
        repository.changeCheckedInShoppingList(id, true)
        val shoppingList = repository.getShoppingList()

        shoppingList.observeForever {
            assertTrue(it[0].isChecked)
        }
    }

    @Test
    fun updateProductName() = runBlocking {
        val id = repository.addShoppingListItem()
        repository.updateProductName(repository.getItem(id).productItemId, "Test1")
        val shoppingList = repository.getShoppingList()

        shoppingList.observeForever {
            assertEquals("Test1", it[0].name)
        }
    }

    @Test
    fun deleteShoppingListItem() = runBlocking {
        val id = repository.addShoppingListItem()
        repository.deleteShoppingListItem(id)
        val shoppingList = repository.getShoppingList()

        shoppingList.observeForever {
            assertEquals(0, it.size)
        }
    }
}
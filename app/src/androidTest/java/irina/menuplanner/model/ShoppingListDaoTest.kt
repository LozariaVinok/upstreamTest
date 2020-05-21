package irina.menuplanner.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import irina.menuplanner.model.entities.Product
import irina.menuplanner.model.entities.ShoppingListItem
import kotlinx.coroutines.runBlocking
import org.junit.*

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShoppingListDaoTest {

    private lateinit var shoppingListDao: ShoppingListDao
    private lateinit var database: MenuPlannerDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, MenuPlannerDatabase::class.java).build()
        shoppingListDao = database.shoppingListDao()

    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertItem() = runBlocking {
        insertTestItem()
        val shoppingList = shoppingListDao.getShoppingList()
        shoppingList.observeForever {
            Assert.assertEquals(1, it.size)
        }
    }

    @Test
    fun updateItem() = runBlocking {
        val id = insertTestItem()
        shoppingListDao.updateItem(id, true)
        val shoppingList = shoppingListDao.getShoppingList()
        shoppingList.observeForever {
            Assert.assertEquals(true, it[0].isChecked)
        }
    }

    @Test
    fun deleteItem() = runBlocking {
        val id = insertTestItem()
        shoppingListDao.deleteItem(id)

        val shoppingList = shoppingListDao.getShoppingList()
        shoppingList.observeForever {
            Assert.assertEquals(0, it.size)
        }
    }

    private suspend fun insertTestItem(): Long {
        val product = Product()
        val productId = database.productDao().insert(product)
        val item = ShoppingListItem(productItemId = productId)
        return shoppingListDao.insertItem(item)
    }
}
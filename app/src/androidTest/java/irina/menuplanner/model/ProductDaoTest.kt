package irina.menuplanner.model


import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import irina.menuplanner.model.entities.Product
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var productDao: ProductDao
    private lateinit var database: MenuPlannerDatabase

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, MenuPlannerDatabase::class.java).build()
        productDao = database.productDao()

    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertProduct() = runBlocking {
        val product = Product(1, "test")
        Assert.assertEquals(1, productDao.insert(product))
    }

    @Test
    fun updateProduct() = runBlocking {
        val product = Product(name = "test")
        val id = productDao.insert(product)
        productDao.updateName(id, "Test1")
        Assert.assertEquals("Test1", productDao.getProduct(id).name)
    }
}
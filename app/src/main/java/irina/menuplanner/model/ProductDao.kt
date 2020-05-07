package irina.menuplanner.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import irina.menuplanner.model.entities.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: Product): Long

    @Query("UPDATE product SET name =:name WHERE productId = :productId")
    suspend fun updateName(productId: Int, name: String)

}
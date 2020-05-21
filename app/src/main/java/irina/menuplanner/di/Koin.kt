package irina.menuplanner.di

import androidx.room.Room
import irina.menuplanner.model.MenuPlannerDatabase
import irina.menuplanner.model.Repository
import irina.menuplanner.ui.shoppinglist.ShoppingListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// just declare it
@JvmField
val myModule = module {
    single {
        Repository(
            Room.databaseBuilder(
                androidContext(),
                MenuPlannerDatabase::class.java, "menu-planner-database"
            ).build()
        )
    }
    viewModel { ShoppingListViewModel(get()) }
}
package irina.menuplanner.di

import irina.menuplanner.model.Repository
import irina.menuplanner.ui.shoppinglist.ShoppingListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// just declare it
@JvmField
val myModule = module {
    single { Repository(androidContext()) }
    viewModel { ShoppingListViewModel(get()) }
}
package irina.menuplanner.ui.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import irina.menuplanner.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ShoppingListFragment : Fragment() {

    private val shoppingListViewModel: ShoppingListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //shoppingListViewModel =
        //    ViewModelProviders.of(this).get(ShoppingListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.shopping_list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ShoppingListAdapter(shoppingListViewModel)
        shoppingListViewModel.shoppingList.observe(viewLifecycleOwner, Observer {
            Timber.d("update")
            (recyclerView.adapter as ShoppingListAdapter).data = it
        })
        return root
    }
}

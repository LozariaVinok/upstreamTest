package irina.menuplanner.ui.shoppinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil.inflate
import androidx.recyclerview.widget.RecyclerView
import irina.menuplanner.R
import irina.menuplanner.databinding.ShoppingListItemBinding
import irina.menuplanner.model.ShoppingListProductItem
import kotlinx.android.synthetic.main.shopping_list_item.view.*


class ShoppingListAdapter(
    private val vm: ShoppingListViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var focusedItem = -1

    init {
        setHasStableIds(true)
    }

    var data: List<ShoppingListProductItem> = ArrayList()
        set(value) {
            if (field.size < value.size) {
                field = value
                focusedItem = value.size - 1
                notifyItemInserted(value.size - 1)
            } else {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val itemBinding: ShoppingListItemBinding = inflate(
                (parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater),
                R.layout.shopping_list_item, parent, false
            )

            ShoppingListViewHolder(itemBinding)
        } else {
            val itemView =
                (parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.shopping_list_footer, parent, false
                )
            ShoppingListViewFooterHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < data.size) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_FOOTER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < data.size) {
            (holder as ShoppingListViewHolder).bind(data[position], vm)
            if (focusedItem == position) {
                vm.onItemClicked(
                    data[position].shoppingListItemId,
                    data[position].name,
                    data[position].productId
                )
            }
            if (focusedItem == position || vm.selectedItemId == data[position].shoppingListItemId) {
                holder.itemView.shopping_list_item_text.post {
                    if (holder.itemView.shopping_list_item_edit_text.requestFocus()) {
                        val inputMethodManager: InputMethodManager =
                            holder.itemView.shopping_list_item_edit_text.context
                                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.showSoftInput(
                            holder.itemView.shopping_list_item_edit_text,
                            InputMethodManager.SHOW_IMPLICIT
                        )
                    }
                }
                focusedItem = -1
            }
        } else {
            (holder as ShoppingListViewFooterHolder).bind(vm)
        }
    }

    override fun getItemId(position: Int): Long {
        return if (position < data.size) {
            data[position].shoppingListItemId.toLong()
        } else {
            -1
        }
    }

    private val VIEW_TYPE_ITEM: Int = 2
    private val VIEW_TYPE_FOOTER: Int = 1
}

class ShoppingListViewHolder(private val itemBinding: ShoppingListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(
        item: ShoppingListProductItem,
        vm: ShoppingListViewModel
    ) {
        itemBinding.item = item
        itemBinding.vm = vm
        itemView.shopping_list_item_check_box.setOnCheckedChangeListener { _, isChecked ->
            vm.onItemChecked(item.shoppingListItemId, isChecked)
        }
        itemView.setOnClickListener {
            vm.onItemClicked(
                item.shoppingListItemId,
                item.name,
                item.productId
            )
        }
    }

}

class ShoppingListViewFooterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(vm: ShoppingListViewModel) {
        itemView.setOnClickListener { vm.onNewItemClicked() }
    }

}

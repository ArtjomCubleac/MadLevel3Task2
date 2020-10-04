package com.example.madlevel3task2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_portal_list.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalListFragment : Fragment() {


    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals) { portal: Portal -> partItemClicked(portal) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_portal_list, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeAddReminderResult()
    }

    private fun observeAddReminderResult() {
        setFragmentResultListener(REQ_PORTAL_KEY) { key, bundle ->
            bundle.getString(BUNDLE_PORTAL_KEY)?.let {
                val portal = Portal(it, it)
                portals.add(portal)
                portalAdapter.notifyDataSetChanged()
            } ?: Log.e("PortalListFragment", "Request triggered, but empty portal text!")
        }
    }

    private fun partItemClicked(portal: Portal) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(portal.PortalTitle))
        startActivity(browserIntent)
    }

    private fun initViews() {
        rvPortals.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvPortals.adapter = portalAdapter

        createItemTouchHelper().attachToRecyclerView(rvPortals)

    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                portals.removeAt(position)
                portalAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }
}
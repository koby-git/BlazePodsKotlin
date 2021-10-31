package com.koby.blazepodskotlin.ui.list

import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koby.blazepodskotlin.R
import com.koby.blazepodskotlin.ui.adapters.CustomAdaper
import com.koby.blazepodskotlin.util.RemoteResult
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class ListFragment
    @Inject constructor():Fragment(R.layout.list_fragment) {

    lateinit var homeRecyclerView: RecyclerView
    public lateinit var listViewModel: ListViewModel

    public val adapter = CustomAdaper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeRecyclerView = view.findViewById(R.id.recyclerview)
        setUpRecyclerView()

        listViewModel = ViewModelProvider(requireActivity())[ListViewModel::class.java]

        addItemFab.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addItemFragment)
        }

        adapter.setOnClick{
            val bundle = Bundle()
            bundle.putString("ITEM_VALUE",it)
            findNavController().navigate(R.id.action_listFragment_to_detailsFragment,bundle)
        }


//        listViewModel.initItemList()
//        listViewModel.roomItemList.observe(viewLifecycleOwner, {
//            if (it.isNotEmpty()){
//                adapter.submitList(it)
//            }
//        })

        listViewModel.itemList.observe(viewLifecycleOwner,{
            when(it.status){
                RemoteResult.Status.SUCCESS->{
                    adapter.submitList(it.data)
                }
                RemoteResult.Status.ERROR->{ }
                RemoteResult.Status.LOADING->{

                }
            }
        })



    }

    private fun setUpRecyclerView() {

        homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        homeRecyclerView.adapter = adapter
//        homeRecyclerView.adapter?.stateRestorationPolicy =
//            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

    }
}
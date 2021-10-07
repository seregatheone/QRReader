package com.example.qrcodereader.fragments.history

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qrcodereader.R
import com.example.qrcodereader.databinding.FragmentHistoryBinding
import com.example.qrcodereader.fragments.viewmodel.HistoryViewModel
import com.example.qrcodereader.localdata.modelfordb.HistoryClass


class HistoryFragment : Fragment() {


    //binding
    private var _binding:FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var _mHistoryViewModel:HistoryViewModel? = null
    private val mHistoryViewModel get() = _mHistoryViewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)

        //RecycleView
        val adapter = HistoryRecViewAdapter()
        //push the adapter
        val recycleView = binding.recycleViewId
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())

        //mHistoryViewModel
        _mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        mHistoryViewModel.readAllData.observe(viewLifecycleOwner, { history ->
            Log.i("dontwork","123412341234")
            adapter.setData(history)
        })

        //floatingButton
        binding.deleteAllHistoryButton.setOnClickListener{
            deleteAll()
        }

        return binding.root

    }
    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mHistoryViewModel.deleteAll()
            Toast.makeText(requireContext(), "All students have been successfully removed", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete all students")
        builder.setMessage("Are you sure want to delete them all")
        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
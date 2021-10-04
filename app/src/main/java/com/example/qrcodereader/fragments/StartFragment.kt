package com.example.qrcodereader.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.qrcodereader.R
import com.example.qrcodereader.databinding.FragmentStartBinding
import kotlinx.coroutines.*

class StartFragment() : Fragment() {

    //binding
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private lateinit var codeScanner: CodeScanner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//      Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        checkPermissions()
        startScanning()
        return binding.root
    }

    private fun startScanning() {

        val scannerView: CodeScannerView = binding.scannerId
        codeScanner = CodeScanner(requireContext(), scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true

        codeScanner.decodeCallback = DecodeCallback {
            codeScanner.stopPreview()
            val res = it.text
            CoroutineScope(Dispatchers.Default).launch{
//              #TODO добавить пересылку в бд
//              val timeStamp = it.timestamp
                val action = StartFragmentDirections.actionStartFragmentToViewQRFragment(res = res)
                try {
                    findNavController().navigate(action)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }
        codeScanner.errorCallback = ErrorCallback {
            it.printStackTrace()
        }
        codeScanner.startPreview()

    }


    private fun checkPermissions() {

        val permission =
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequestForPermission()
        }
    }

    private fun makeRequestForPermission() {

        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.CAMERA),
            1
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(requireContext(), "Accept permission request", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else -> Toast.makeText(requireContext(), "Accept denied request", Toast.LENGTH_LONG)
                .show()
        }
    }
}
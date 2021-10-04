package com.example.qrcodereader.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.qrcodereader.databinding.FragmentViewQRBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class ViewQRFragment : Fragment() {
    //binding
    private var _binding: FragmentViewQRBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ViewQRFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewQRBinding.inflate(layoutInflater, container, false)

        binding.copy.setOnClickListener {
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("text", binding.qrCodeData.text)
            clipboard.setPrimaryClip(clip)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val multiFormatWriter = MultiFormatWriter()
        val text = args.res
        binding.qrCodeData.setText(text)
        try{

            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,700,700);
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix);
            binding.imageView.setImageBitmap(bitmap);
        }catch (e : Exception){
            e.printStackTrace();
        }
    }
}

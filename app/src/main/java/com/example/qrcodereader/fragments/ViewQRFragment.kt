package com.example.qrcodereader.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.qrcodereader.databinding.FragmentViewQRBinding
import com.example.qrcodereader.localdata.modelfordb.TimeToSdf
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.text.SimpleDateFormat

class ViewQRFragment : Fragment(), TimeToSdf{
    //binding
    private var _binding: FragmentViewQRBinding? = null
    private val binding get() = _binding!!

    //Достаём данные из arguments
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
        //создаём наполнение по пришедшим данным
        super.onViewCreated(view, savedInstanceState)
        val multiFormatWriter = MultiFormatWriter()
        val historyClass = args.history
        val sdf = timeToSDF(historyClass.time)
        binding.qrCodeData.setText(historyClass.qrContent)
        binding.timestamp.text = sdf

        val bitMatrix = multiFormatWriter.encode(historyClass.qrContent, BarcodeFormat.QR_CODE,900,900);
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.createBitmap(bitMatrix);
        binding.imageView.setImageBitmap(bitmap);

    }

    override fun timeToSDF(timeRequest: Long): String {
        return  SimpleDateFormat("yyyy.MM.dd HH:mm").format(timeRequest)
    }
}

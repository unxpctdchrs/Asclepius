package com.dicoding.asclepius.view.main.scan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.dicoding.asclepius.databinding.FragmentScanBinding
import com.dicoding.asclepius.view.main.scan.result.ResultActivity
import com.yalantis.ucrop.UCrop
import java.io.File
import kotlin.random.Random

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding

    private var currentImageUri: Uri? = null
    private var croppedImageUri: Uri? = null

    private val ucropContracts = object : ActivityResultContract<List<Uri>, Uri>() {
        override fun createIntent(context: Context, input: List<Uri>): Intent {
            val inputUri = input[0]
            val outputUri = input[1]

            val ucrop = UCrop.of(inputUri, outputUri)
                .withAspectRatio(5f, 5f)
                .withMaxResultSize(800, 800)

            return ucrop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
            return if (intent != null && resultCode == Activity.RESULT_OK) {
                UCrop.getOutput(intent)!!
            } else {
                Uri.EMPTY
            }
        }
    }

    private var launchGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private val cropImage = registerForActivityResult(ucropContracts) { uri ->
        binding?.previewImageView?.setImageURI(uri)
        croppedImageUri = uri
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.galleryButton?.setOnClickListener { startGallery() }
        binding?.analyzeButton?.setOnClickListener { analyzeImage() }
    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage() {
        val imageId = Random(10)
        currentImageUri?.let {
            val inputUri = it
            val outputUri = File(context?.filesDir, "croppedImage$imageId.png").toUri()

            val listUri = listOf(inputUri, outputUri)
            cropImage.launch(listUri)
        }
    }

    private fun analyzeImage() {
        if (currentImageUri == null) {
            Toast.makeText(requireContext(), "No Image Inserted, please insert an Image first", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(requireActivity(), ResultActivity::class.java)
            intent.putExtra(ResultActivity.IMAGE_URI, croppedImageUri.toString())
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
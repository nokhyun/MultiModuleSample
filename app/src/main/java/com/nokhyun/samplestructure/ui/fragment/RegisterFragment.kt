package com.nokhyun.samplestructure.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentRegisterBinding
import timber.log.Timber

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

//    private val permissionLaunch = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//       Timber.e(if(isGranted) "동의" else "거절")
//    }

    private val permissionLaunchMulti = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGrantedMap ->
        isGrantedMap.filter { !it.value }
            .map {
                it.value
            }.also {
                Timber.e("result: $it")
            }
    }

    override fun init() {
        binding.btnNext.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                    Timber.e("체크 권한 있음")
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    Timber.e("왜 취소함")
                    MaterialAlertDialogBuilder(requireContext()).setTitle("왜 취소했어?")
                        .setMessage("왜??")
                        .setPositiveButton("확인") { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()

                }
                else -> {
                    Timber.e("권한 요청")
//                    ActivityCompat.requestPermissions(
//                        requireActivity(), arrayOf(
//                            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
//                        ), 1001
//                    )
//                    permissionLaunch.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                    permissionLaunchMulti.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION))
                }
            }
        }
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_register)
}
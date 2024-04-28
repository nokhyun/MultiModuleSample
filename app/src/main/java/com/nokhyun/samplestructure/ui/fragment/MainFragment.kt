package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nokhyun.samplestructure.BR
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentMainBinding
import com.nokhyun.samplestructure.ui.fragment.viewmodel.MainViewModel
import com.nokhyun.samplestructure.ui.fragment.viewmodel.NavigationPoint
import com.nokhyun.samplestructure.utils.launchStarted
import com.nokhyun.samplestructure.utils.safeNavigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Navigation MainFragment
 * */
class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun init() {
        binding.setVariable(BR.viewModel, mainViewModel)
//        lifecycleScope.launch {
//            launch {
//                delay(3000)
//                binding.clMain.addView(TextView(requireContext()).apply {
//                    text = "아니 무슨!!!!"
//                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28f)
//                })
//            }
//
//            launch {
//                delay(2000)
//                binding.clMain.addView(TextView(requireContext()).apply {
//                    text = "아니 무슨일이람"
//                    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 36f)
//                })
//            }
//        }

//        binding.clMain.doOnNextLayout {
//            TransitionManager.beginDelayedTransition(binding.clMain, Fade(Fade.MODE_OUT))
//        }

        // marquee
        binding.tvMarquee.isSelected = true
        binding.tvMarquee.setHorizontallyScrolling(true)
        binding.tvMarqueeSub.isSelected = true
        binding.tvMarqueeSub.setHorizontallyScrolling(true)
    }

    override fun navigator() {
        launchStarted {
            launch {
                mainViewModel.navigateToFragment
                    .map { navigationPoint ->
                        when (navigationPoint) {
                            NavigationPoint.EXO_PLAYER -> MainFragmentDirections.actionMainFragmentToExoPlayerFragment()
                            NavigationPoint.SKELETON -> MainFragmentDirections.actionMainFragmentToSkeletonFragment()
                            NavigationPoint.TRANSITION -> MainFragmentDirections.actionMainFragmentToTransitionFragment()
                            NavigationPoint.FLOW -> MainFragmentDirections.actionMainFragmentToFlowFragment()
                            NavigationPoint.NOTIFICATION -> MainFragmentDirections.actionMainFragmentToNotificationFragment()
                            NavigationPoint.VP -> MainFragmentDirections.actionMainFragmentToVpFragment()
                            NavigationPoint.COROUTINE -> MainFragmentDirections.actionMainFragmentToCoroutineFragment()
                            NavigationPoint.SHARE -> MainFragmentDirections.actionMainFragmentToShareFragment()
                        }
                    }
                    .collectLatest { id ->
                        findNavController().safeNavigate(id)
                    }
            }
        }
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_main)
}
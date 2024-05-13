package com.nokhyun.samplestructure.ui.fragment

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentTransitionBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TransitionFragment : BaseFragment<FragmentTransitionBinding>() {
    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    override fun init() {
        binding.btnVisible.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.clFade, Fade(Fade.IN))
            binding.tvText.isVisible = true
        }

        binding.btnGone.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.clFade, Fade(Fade.OUT))
            binding.tvText.isVisible = false
        }

        binding.btnTransitionVisible.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.clTransition, ChangeBounds())
            val root: ConstraintLayout = binding.clTransition
            val set = ConstraintSet()
            set.clone(root)
            set.connect(
                binding.tvTransitionText.id,
                ConstraintSet.BOTTOM,
                root.id,
                ConstraintSet.BOTTOM
            )
            set.applyTo(root)
        }

        binding.btnTransitionGone.setOnClickListener {
//            TransitionManager.beginDelayedTransition(binding.clTransition, ChangeBounds())
//            val root: ConstraintLayout = binding.clTransition
//            val set = ConstraintSet()
//            set.clone(root)
//            set.connect(
//                binding.tvTransitionText.id,
//                ConstraintSet.BOTTOM,
//                root.id,
//                ConstraintSet.TOP
//            )
//            set.applyTo(root)

            binding.clTransition.beginDelayedTransition(ChangeBounds()) { viewGroup ->
                if (viewGroup is ConstraintLayout) {
                    val root: ConstraintLayout = viewGroup
                    val set = ConstraintSet()
                    set.clone(root)
                    set.connect(
                        binding.tvTransitionText.id,
                        ConstraintSet.BOTTOM,
                        root.id,
                        ConstraintSet.TOP
                    )
                    set.applyTo(root)

//                    ConstraintSet().also {
//                        it.clone(viewGroup)
//                        it.connect(
//                            binding.tvText.id,
//                            ConstraintSet.BOTTOM,
//                            viewGroup.id,
//                            ConstraintSet.TOP
//                        )
//                        it.applyTo(viewGroup)
//                    }
                }
            }
        }
        flipAnim()
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_transition)

    // test
    private fun ViewGroup.beginDelayedTransition(transition: Transition, block: (ViewGroup) -> Unit) {
        TransitionManager.beginDelayedTransition(this, transition)
        block(this)
    }

    private fun flipAnim() {
        var scale = resources.displayMetrics.density
        val front = binding.ivFront
        val back = binding.ivBack

        front.cameraDistance = 8000 * scale
        back.cameraDistance = 8000 * scale

        frontAnim = AnimatorInflater.loadAnimator(requireContext(), R.animator.front_animator) as AnimatorSet
        backAnim = AnimatorInflater.loadAnimator(requireContext(), R.animator.back_animator) as AnimatorSet

        viewLifecycleOwner.lifecycleScope.launch {
            frontAnim.setTarget(front)
            backAnim.setTarget(back)
            frontAnim.start()
            backAnim.start()
            delay(5000)
            backAnim.setTarget(front)
            frontAnim.setTarget(back)
            frontAnim.start()
            backAnim.start()
        }
    }
}
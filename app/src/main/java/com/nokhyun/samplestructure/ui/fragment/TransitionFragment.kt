package com.nokhyun.samplestructure.ui.fragment

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.nokhyun.samplestructure.R
import com.nokhyun.samplestructure.databinding.FragmentTransitionBinding

class TransitionFragment : BaseFragment<FragmentTransitionBinding>() {
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
            TransitionManager.beginDelayedTransition(binding.clTransition, ChangeBounds())
            val root: ConstraintLayout = binding.clTransition
            val set = ConstraintSet()
            set.clone(root)
            set.connect(
                binding.tvTransitionText.id,
                ConstraintSet.BOTTOM,
                root.id,
                ConstraintSet.TOP
            )
            set.applyTo(root)
        }
    }

    override fun navigator() {
    }

    override fun setView(view: (layoutId: Int) -> View): View = view(R.layout.fragment_transition)
}
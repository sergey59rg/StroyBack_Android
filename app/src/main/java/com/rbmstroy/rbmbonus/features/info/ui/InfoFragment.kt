package com.rbmstroy.rbmbonus.features.info.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.info.presenter.InfoAdapter
import com.rbmstroy.rbmbonus.features.info.presenter.InfoPresenter
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.info_fragment.*

class InfoFragment : Fragment(), InfoView {

    private lateinit var presenter: InfoPresenter
    var layouts: IntArray = intArrayOf(R.layout.first_slide, R.layout.second_slide, R.layout.third_slide, R.layout.fourth_slide)
    lateinit var dots: Array<ImageView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        createDots(0)
        back.isVisible = false
        back!!.setSafeOnClickListener {
            loadBackSlide()
        }
        next!!.setSafeOnClickListener {
            loadNextSlide()
        }
    }

    private fun initDependency() {
        val infoAdapter = InfoAdapter(layouts, requireContext())
        viewPager.adapter = infoAdapter
        presenter = InfoPresenter(this)
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                createDots(position)
                if (position == 0) {
                    back.isVisible = false
                } else {
                    back.isVisible = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    fun loadHome() {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_home_fragment)
    }

    fun loadBackSlide() {
        val backSlide: Int = viewPager.currentItem - 1
        if (backSlide < layouts.size) {
            viewPager.setCurrentItem(backSlide)
        }
    }

    fun loadNextSlide() {
        val nextSlide: Int = viewPager.currentItem + 1
        if (nextSlide < layouts.size) {
            viewPager.setCurrentItem(nextSlide)
        } else {
            loadHome()
        }
    }

    fun createDots(position: Int) {
        if (dotsLayout != null) {
            dotsLayout.removeAllViews()
        }
        dots = Array(layouts.size, {i -> ImageView(requireContext()) })
        for (i in 0..layouts.size - 1) {
            dots[i] = ImageView(requireContext())
            if (i == position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.active_dots))
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.inactive_dots))
            }
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(8,0,8,0)
            dotsLayout.addView(dots[i], params)
        }
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

}
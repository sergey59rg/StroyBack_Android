package com.rbmstroy.rbmbonus.features.promotion.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.PromotionRepository
import com.rbmstroy.rbmbonus.data.network.RetrofitFactory
import com.rbmstroy.rbmbonus.domain.PromotionInterceptorImpl
import com.rbmstroy.rbmbonus.features.promotion.presenter.PromotionAdapter
import com.rbmstroy.rbmbonus.features.promotion.presenter.PromotionPresenter
import com.rbmstroy.rbmbonus.model.Promotion
import com.rbmstroy.rbmbonus.model.PromotionResponse
import com.rbmstroy.rbmbonus.utils.ConnectionDetector
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.MetricsUtils
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.promotion_fragment.*


class PromotionFragment : Fragment(), PromotionView {

    private lateinit var presenter: PromotionPresenter
    private lateinit var data: List<Promotion>
    lateinit var timer: CountDownTimer
    private var seconds: Long = 15
    lateinit var bars: Array<ProgressBar>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.promotion_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        back.isVisible = false
        next.isVisible = false
        timer = object: CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (bars.isNotEmpty()) {
                    bars[viewPager.currentItem].progress =
                        (seconds - ((millisUntilFinished / 1000) + 0)).toInt()
                }
            }
            override fun onFinish() {
                loadNextSlide()
            }
        }
        back!!.setSafeOnClickListener {
            loadBackSlide()
        }
        next!!.setSafeOnClickListener {
            loadNextSlide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

    private fun initDependency() {
        val promotionRepository = PromotionRepository(RetrofitFactory.apiRetrofit)
        val promotionInteractor = PromotionInterceptorImpl(promotionRepository)
        presenter = PromotionPresenter(this, promotionInteractor)
        if (ConnectionDetector.ConnectingToInternet(requireContext())) {
            presenter.promotion(arguments?.getInt("id")!!)
        } else {
            showError(getString(R.string.no_internet))
        }
    }

    fun exit() {
        requireActivity().onBackPressed()
    }

    fun loadBackSlide() {
        val nextSlide: Int = viewPager.currentItem - 1
        if (nextSlide < data.size) {
            viewPager.setCurrentItem(nextSlide)
        }
    }

    fun loadNextSlide() {
        val nextSlide: Int = viewPager.currentItem + 1
        if (nextSlide < data.size) {
            viewPager.setCurrentItem(nextSlide)
        } else {
            exit()
        }
    }

    fun createProgress(position: Int) {
        timer.cancel()
        timer.start()
        if (progressLayout != null) {
            progressLayout.removeAllViews()
        }
        bars = Array(data.size, {i -> ProgressBar(requireContext()) })
        for (i in 0..data.size - 1) {
            bars[i] = ProgressBar(requireContext(), null, android.R.attr.progressBarStyleHorizontal)
            bars[i].isIndeterminate = false
            bars[i].max = seconds.toInt()
            bars[i].progressDrawable = requireContext().getDrawable(R.drawable.progress)
            if (i < position) {
                bars[i].progress = seconds.toInt()
            } else {
                bars[i].progress = 0
            }
            if (i != position) {
                bars[i].setSafeOnClickListener {
                    viewPager.setCurrentItem(i)
                }
            }
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(MetricsUtils.getScreenWidth()/data.size - 24, 10)
            params.setMargins(8,0,8,0)
            progressLayout.addView(bars[i], params)
        }
    }

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        try {
            progressBar.visibility = View.GONE
        } catch (e: Exception) {

        }
    }

    override fun showError(error: String) {
        CustomDialog(requireContext()).show(getString(R.string.error), error, arrayOf(getString(R.string.ok))) { }
    }

    override fun load(data: PromotionResponse) {
        if (data.items.isNotEmpty()) {
            this.data = data.items
            if (this.data.size == 1) {
                next.setText(R.string.close)
            }
            showProgress()
            viewPager.isVisible = false
            val handler = Handler()
            handler.postDelayed({
                hideProgress()
                viewPager.isVisible = true
                viewPager.setBackgroundColor(requireContext().getResources().getColor(R.color.blue))
                next.isVisible = true
                createProgress(0)
            }, 1000)
            val promotionAdapter = PromotionAdapter(requireContext(), data.items)
            promotionAdapter.viewSetClickListener = ::viewSetOnClick
            viewPager.adapter = promotionAdapter
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    createProgress(position)
                    if (position == 0) {
                        back.isVisible = false
                    } else {
                        back.isVisible = true
                    }
                    if (position != data.items.size - 1) {
                        next.setText(R.string.next)
                    } else {
                        next.setText(R.string.close)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {

                }
            })

        } else {
            CustomDialog(requireContext()).show(getString(R.string.error), getString(R.string.error_no_data_promotion), arrayOf(getString(R.string.ok))) {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun viewSetOnClick(data: Promotion) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
        startActivity(browserIntent)
    }
}
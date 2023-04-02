package com.rbmstroy.rbmbonus.features.registration.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.features.registration.presenter.CongratulatePresenter
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import kotlinx.android.synthetic.main.congratulate_fragment.*

class CongratulateFragment: Fragment(), CongratulateView {

    private lateinit var presenter: CongratulatePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.congratulate_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        next_button!!.setSafeOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.backQueue.reversed().forEach {
                if (it.destination.label != null) {
                    navController.popBackStack(it.destination.id, true)
                }
            }
            navController.navigate(R.id.nav_authorization_fragment)
        }
    }

    private fun initDependency() {
        presenter = CongratulatePresenter(this)
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

}
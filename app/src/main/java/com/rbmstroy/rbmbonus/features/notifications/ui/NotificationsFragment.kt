package com.rbmstroy.rbmbonus.features.notifications.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.data.NotificationsInterceptorImpl
import com.rbmstroy.rbmbonus.data.sqlite.DatabaseHandler
import com.rbmstroy.rbmbonus.features.notifications.presenter.NotificationsAdapter
import com.rbmstroy.rbmbonus.features.notifications.presenter.NotificationsPresenter
import com.rbmstroy.rbmbonus.model.Notifications
import com.rbmstroy.rbmbonus.utils.CustomDialog
import com.rbmstroy.rbmbonus.utils.SafeClickListener
import com.rbmstroy.rbmbonus.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.notifications_fragment.*

class NotificationsFragment: Fragment(), NotificationsView {

    private lateinit var presenter: NotificationsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.notifications_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDependency()
        back!!.setSafeOnClickListener {
            requireActivity().onBackPressed()
        }
        option!!.setSafeOnClickListener {
            CustomDialog(requireContext()).show(getString(R.string.message), getString(R.string.mark_notifications), arrayOf(getString(R.string.confirm), getString(R.string.cancel))) {
                when (it) {
                    CustomDialog.ResponseType.YES -> {
                       presenter.update()
                    }
                    CustomDialog.ResponseType.NO -> {
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initDependency() {
        val dbHandler = DatabaseHandler(requireContext(), null)
        val notificationsInterceptor = NotificationsInterceptorImpl(dbHandler)
        val adapter = NotificationsAdapter(arrayListOf())
        listRecycler.layoutManager = LinearLayoutManager(requireContext())
        listRecycler.adapter = adapter
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notificationsInterceptor.deleteNotifications(adapter.remove(viewHolder.position))
                presenter.load()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(listRecycler)
        presenter = NotificationsPresenter(this, notificationsInterceptor, adapter)
        presenter.load()
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

    override fun promotion(data: Notifications) {
        val bundle = Bundle()
        bundle.putInt("id", data.id)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.nav_promotion_fragment, bundle)
    }

}
package com.rbmstroy.rbmbonus.features.promotion.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.viewpager.widget.PagerAdapter
import com.rbmstroy.rbmbonus.R
import com.rbmstroy.rbmbonus.model.Promotion
import com.squareup.picasso.Picasso


class PromotionAdapter(val context: Context, val data: List<Promotion>) : PagerAdapter() {

    var viewSetClickListener: ((Promotion) ->Unit)? = null

    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view: View = `object` as View
        container.removeView(view)
    }

    @SuppressLint("ServiceCast")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.promotion_slider_item, container, false)
        val image: ImageView = view.findViewById<View>(R.id.image) as ImageView
        val background_image: ImageView = view.findViewById<View>(R.id.background_image) as ImageView
        val title: TextView = view.findViewById<View>(R.id.title) as TextView
        val text: TextView = view.findViewById<View>(R.id.text) as TextView
        val detail_button: Button = view.findViewById<View>(R.id.detail_button) as Button
        if (data[position].url.isEmpty()) {
            detail_button.isVisible = false
        } else {
            detail_button.isVisible = true
        }
        if (data[position].img.isNotEmpty()) {
            Picasso.with(image.context).load(data[position].img).into(image)
        }
        if (data[position].backgroundimg.isNotEmpty()) {
            Picasso.with(background_image.context).load(data[position].backgroundimg)
                .into(background_image)
        }
        title.setText(data[position].title)
        text.setText(data[position].text)
        detail_button.setOnClickListener { viewSetClickListener?.invoke(data[position]) }
        container.addView(view)
        return view
    }
}
package com.example.matchparfait.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.matchparfait.R

class ScoreStars @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) ,
    View.OnClickListener{

    private var star1 : ImageView
    private var star2 : ImageView
    private var star3 : ImageView
    private var star4 : ImageView
    private var star5 : ImageView

    init {
        val view : View = LayoutInflater.from(context).inflate(R.layout.controller_stars_score, this, true)
        this.star1 = view.findViewById(R.id.star1)
        this.star2 = view.findViewById(R.id.star2)
        this.star3 = view.findViewById(R.id.star3)
        this.star4 = view.findViewById(R.id.star4)
        this.star5 = view.findViewById(R.id.star5)
    }

    fun score(num : Int){
        val stars = listOf(star1, star2, star3, star4, star5)

        for (i in stars.indices) {
            if (i < num) {
                stars[i].setImageResource(R.drawable.ic_star)
            } else {
                stars[i].setImageResource(R.drawable.ic_star_line)
            }
        }
    }

    override fun onClick(p0: View?) {

    }
}
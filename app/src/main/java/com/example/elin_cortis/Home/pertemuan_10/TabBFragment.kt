package com.example.elin_cortis.Home.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.elin_cortis.R

class TabBFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardQ1 = view.findViewById<View>(R.id.cardQ1)
        val tvAns1 = view.findViewById<TextView>(R.id.tvAns1)

        val cardQ2 = view.findViewById<View>(R.id.cardQ2)
        val tvAns2 = view.findViewById<TextView>(R.id.tvAns2)

        val cardQ3 = view.findViewById<View>(R.id.cardQ3)
        val tvAns3 = view.findViewById<TextView>(R.id.tvAns3)

        cardQ1.setOnClickListener {
            if (tvAns1.visibility == View.GONE) {
                tvAns1.visibility = View.VISIBLE
            } else {
                tvAns1.visibility = View.GONE
            }
        }

        cardQ2.setOnClickListener {
            if (tvAns2.visibility == View.GONE) {
                tvAns2.visibility = View.VISIBLE
            } else {
                tvAns2.visibility = View.GONE
            }
        }

        cardQ3.setOnClickListener {
            if (tvAns3.visibility == View.GONE) {
                tvAns3.visibility = View.GONE
            }
        }
    }
}
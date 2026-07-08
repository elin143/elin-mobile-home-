package com.example.elin_cortis.About.Tutorial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.elin_cortis.R
import com.example.elin_cortis.databinding.ActivityTutorialMessageBinding

class TutorialMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTutorialMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragmentsList = listOf(Tutorial1Fragment(), Tutorial2Fragment(), Tutorial3Fragment())
        val adapter = TutorialFragmentAdapter(this, fragmentsList)
        binding.tutorialMessageViewPager.adapter = adapter
        binding.dotIndicator.attachTo(binding.tutorialMessageViewPager)

        binding.btnSkip.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            val currentItem = binding.tutorialMessageViewPager.currentItem
            if (currentItem < fragmentsList.size - 1) {
                binding.tutorialMessageViewPager.currentItem = currentItem + 1
            } else {
                finish()
            }
        }

        binding.tutorialMessageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == fragmentsList.size - 1) {
                    binding.btnNext.text = "Selesai"
                    binding.btnSkip.visibility = View.INVISIBLE
                } else {
                    binding.btnNext.text = "Lanjut"
                    binding.btnSkip.visibility = View.VISIBLE
                }
            }
        })
    }

}
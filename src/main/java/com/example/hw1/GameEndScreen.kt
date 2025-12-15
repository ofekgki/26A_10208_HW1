package com.example.hw1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.hw1.utilities.Constants
import com.google.android.material.textview.MaterialTextView

class GameEndScreen : AppCompatActivity() {

    private lateinit var End_LBL_Massage: MaterialTextView

    private lateinit var End_BTN_StarGame: AppCompatImageButton

    private lateinit var End_LBL_StartGame: MaterialTextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_end_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()
    }

    private fun initViews() {
        val bundle: Bundle? = intent.extras

        val message = bundle?.getString(Constants.BundleKeys.MESSAGE_KEY, "🤷🏻‍♂️ Unknown Status!")

        End_LBL_Massage.text = buildString {
            append(message)

        }
        End_LBL_StartGame.setOnClickListener { view: View ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun findViews() {
        End_LBL_Massage = findViewById(R.id.End_LBL_Massage)
        End_BTN_StarGame = findViewById(R.id.End_BTN_StarGame)
        End_LBL_StartGame = findViewById(R.id.End_LBL_StartGame)
    }
}


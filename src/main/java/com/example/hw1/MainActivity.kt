package com.example.hw1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //Hearts

    private lateinit var Main_IMG_Heart_L: AppCompatImageView

    private lateinit var Main_IMG_Heart_M: AppCompatImageView

    private lateinit var Main_IMG_Heart_R: AppCompatImageView

    //Roosters

    private lateinit var Main_IMG_Rooster_R: AppCompatImageView

    private lateinit var Main_IMG_Rooster_M: AppCompatImageView

    private lateinit var Main_IMG_Rooster_L: AppCompatImageView

    //Pans Row 1

    private lateinit var Main_IMG_Pan1: AppCompatImageView

    private lateinit var Main_IMG_Pan2: AppCompatImageView

    private lateinit var Main_IMG_Pan3: AppCompatImageView

    //Pans Row 2

    private lateinit var Main_IMG_Pan4: AppCompatImageView

    private lateinit var Main_IMG_Pan5: AppCompatImageView

    private lateinit var Main_IMG_Pan6: AppCompatImageView

    //Pans Row 3

    private lateinit var Main_IMG_Pan7: AppCompatImageView

    private lateinit var Main_IMG_Pan8: AppCompatImageView

    private lateinit var Main_IMG_Pan9: AppCompatImageView

    //Pans Row 4

    private lateinit var Main_IMG_Pan10: AppCompatImageView

    private lateinit var Main_IMG_Pan11: AppCompatImageView

    private lateinit var Main_IMG_Pan12: AppCompatImageView

    //Pans Row 5

    private lateinit var Main_IMG_Pan13: AppCompatImageView

    private lateinit var Main_IMG_Pan14: AppCompatImageView

    private lateinit var Main_IMG_Pan15: AppCompatImageView

    //Pans Row 6

    private lateinit var Main_IMG_Pan16: AppCompatImageView

    private lateinit var Main_IMG_Pan17: AppCompatImageView

    private lateinit var Main_IMG_Pan18: AppCompatImageView

    //Fabs

    private lateinit var Main_FAB_Left: FloatingActionButton

    private lateinit var Main_FAB_Right: FloatingActionButton

    //Others

    private var roosterPosition: Int = 1 //0 - Left, 1 - Middle, 2 - Right
//    private val panVisible =  Array<AppCompatImageView>(
//        18,initArray()) //0 - Invisible, 1 - Visible
//

    private var panInLastLine: Int = -1 ////0 - Left, 1 - Middle, 2 - Right, -1 No Pan In Last Lane


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViews()
        initViews()
        startPans()


    }

//    private fun startPans() {
//        var panNumber = Random.nextInt(3)+1
//        panVisible[panNumber] = 1
//
//    }


    private fun findViews() {
        Main_IMG_Heart_L = findViewById(R.id.Main_IMG_Heart_L)
        Main_IMG_Heart_M = findViewById(R.id.Main_IMG_Heart_M)
        Main_IMG_Heart_R = findViewById(R.id.Main_IMG_Heart_R)
        Main_IMG_Rooster_R = findViewById(R.id.Main_IMG_Rooster_R)
        Main_IMG_Rooster_M = findViewById(R.id.Main_IMG_Rooster_M)
        Main_IMG_Rooster_L = findViewById(R.id.Main_IMG_Rooster_L)
        Main_IMG_Pan1 = findViewById(R.id.Main_IMG_Pan1)
        Main_IMG_Pan2 = findViewById(R.id.Main_IMG_Pan2)
        Main_IMG_Pan3 = findViewById(R.id.Main_IMG_Pan3)
        Main_IMG_Pan4 = findViewById(R.id.Main_IMG_Pan4)
        Main_IMG_Pan5 = findViewById(R.id.Main_IMG_Pan5)
        Main_IMG_Pan6 = findViewById(R.id.Main_IMG_Pan6)
        Main_IMG_Pan7 = findViewById(R.id.Main_IMG_Pan7)
        Main_IMG_Pan8 = findViewById(R.id.Main_IMG_Pan8)
        Main_IMG_Pan9 = findViewById(R.id.Main_IMG_Pan9)
        Main_IMG_Pan10 = findViewById(R.id.Main_IMG_Pan10)
        Main_IMG_Pan11 = findViewById(R.id.Main_IMG_Pan11)
        Main_IMG_Pan12 = findViewById(R.id.Main_IMG_Pan12)
        Main_IMG_Pan13 = findViewById(R.id.Main_IMG_Pan13)
        Main_IMG_Pan14 = findViewById(R.id.Main_IMG_Pan14)
        Main_IMG_Pan15 = findViewById(R.id.Main_IMG_Pan15)
        Main_IMG_Pan16 = findViewById(R.id.Main_IMG_Pan16)
        Main_IMG_Pan17 = findViewById(R.id.Main_IMG_Pan17)
        Main_IMG_Pan18 = findViewById(R.id.Main_IMG_Pan18)
        Main_FAB_Left = findViewById(R.id.Main_FAB_Left)
        Main_FAB_Right = findViewById(R.id.Main_FAB_Right)

    }

    private fun initViews() {
        updateRooster()
        //panVisible.fill(0)
        Main_FAB_Right.setOnClickListener { view -> moveRight() }
        Main_FAB_Left.setOnClickListener { view -> moveLeft() }
    }

    //update rooster position in the Main Activity
    private fun updateRooster(){
        Main_IMG_Rooster_L.visibility = View.INVISIBLE
        Main_IMG_Rooster_M.visibility = View.INVISIBLE
        Main_IMG_Rooster_R.visibility = View.INVISIBLE

        when (roosterPosition) {
            0 -> Main_IMG_Rooster_L.visibility = View.VISIBLE
            1 -> Main_IMG_Rooster_M.visibility = View.VISIBLE
            2 -> Main_IMG_Rooster_R.visibility = View.VISIBLE
        }
    }

    //updating the rooster position in the variable for right move
    private fun moveRight() {
        if (roosterPosition < 2) {
            roosterPosition++
            updateRooster()
        }
    }

    //updating the rooster position in the variable for left move
    private fun moveLeft() {
        if (roosterPosition > 0) {
            roosterPosition--
            updateRooster()
        }
    }


}
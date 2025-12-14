package com.example.hw1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.hw1.utilities.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //Hearts
    private lateinit var Main_IMG_hearts: Array<AppCompatImageView>

    //Roosters

    private lateinit var Main_IMG_Rooster_R: AppCompatImageView

    private lateinit var Main_IMG_Rooster_M: AppCompatImageView

    private lateinit var Main_IMG_Rooster_L: AppCompatImageView

    private lateinit var Main_IMG_pans: Array<AppCompatImageView>

    private val isVisible = IntArray(18)

    /*
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
*/

    //Fabs

    private lateinit var Main_FAB_Left: FloatingActionButton

    private lateinit var Main_FAB_Right: FloatingActionButton

    //Others

    private var roosterPosition: Int = 1 //0 - Left, 1 - Middle, 2 - Right

    private var panInLastLine: Int = -1 ////0 - Left, 1 - Middle, 2 - Right, -1 No Pan In Last Lane

    private lateinit var timerJob: Job

    private var spaceFlag: Int = 0


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
        startGame()


    }

    // Start Timer Coroutine For Pans UI Update
    private fun startGame() {

        timerJob = lifecycleScope.launch {
            while (isActive) {
                Log.d("Timer Runnable", "Active: \$isActive")
                // Refresh UI:
                updatePanUI()
                delay(Constants.Timer.DELAY)
            }
        }
    }

    // Choose random Pan To Add & Call For Func To - Update The Game Board, Check If Hit Happened
    private fun updatePanUI() {
        updateVisibleArray()
        if (spaceFlag == 0) {
            val randomPan = Random.nextInt(3)
            isVisible[randomPan] = 1
            spaceFlag = 1
        } else
            spaceFlag = 0
        refreshUI()

        //checkForHit()
    }

    //Change Visibility Of Pans And 'Move' them Down
    private fun refreshUI() {
        Main_IMG_pans.forEachIndexed { index, img ->
            if (isVisible[index] == 1) {
                img.visibility = View.VISIBLE
                when (index) {
                    15 -> panInLastLine = 0
                    16 -> panInLastLine = 1
                    17 -> panInLastLine = 2
                }
            } else
                img.visibility = View.INVISIBLE

        }
    }


    //Check If There Is A Collision Between A Rooster And A Pan
    private fun checkForHit() {
        if (panInLastLine == roosterPosition)
            makeHit()

    }

    //Calling a Function For Heart Decrease & Calling Functions For Toast & Vibrate
    private fun makeHit() {
        heartDecrease()
        makeToast()
        makeVibration()

    }

    private fun heartDecrease() {
        TODO("Not yet implemented")
    }

    private fun makeToast() {
        TODO("Not yet implemented")
    }

    private fun makeVibration() {
        TODO("Not yet implemented")
    }

    //Update The Array That Track Visibility Of The Pans
    private fun updateVisibleArray() {
        Main_IMG_pans.forEachIndexed { index, img ->
            if (img.visibility == View.VISIBLE && index < 15) {
                isVisible[index] = 0
                isVisible[index + 3] = 1
            }
            if (img.visibility == View.VISIBLE && index >= 15) {
                isVisible[index] = 0
            }

        }
    }


    private fun findViews() {

        Main_IMG_hearts = arrayOf(
            findViewById(R.id.Main_IMG_Heart_L),
            findViewById(R.id.Main_IMG_Heart_M),
            findViewById(R.id.Main_IMG_Heart_R)
        )
        Main_IMG_Rooster_R = findViewById(R.id.Main_IMG_Rooster_R)
        Main_IMG_Rooster_M = findViewById(R.id.Main_IMG_Rooster_M)
        Main_IMG_Rooster_L = findViewById(R.id.Main_IMG_Rooster_L)

        Main_IMG_pans = arrayOf(
            findViewById(R.id.Main_IMG_Pan1),
            findViewById(R.id.Main_IMG_Pan2),
            findViewById(R.id.Main_IMG_Pan3),
            findViewById(R.id.Main_IMG_Pan4),
            findViewById(R.id.Main_IMG_Pan5),
            findViewById(R.id.Main_IMG_Pan6),
            findViewById(R.id.Main_IMG_Pan7),
            findViewById(R.id.Main_IMG_Pan8),
            findViewById(R.id.Main_IMG_Pan9),
            findViewById(R.id.Main_IMG_Pan10),
            findViewById(R.id.Main_IMG_Pan11),
            findViewById(R.id.Main_IMG_Pan12),
            findViewById(R.id.Main_IMG_Pan13),
            findViewById(R.id.Main_IMG_Pan14),
            findViewById(R.id.Main_IMG_Pan15),
            findViewById(R.id.Main_IMG_Pan16),
            findViewById(R.id.Main_IMG_Pan17),
            findViewById(R.id.Main_IMG_Pan18)
        )

        Main_FAB_Right = findViewById(R.id.Main_FAB_Right)
        Main_FAB_Left = findViewById(R.id.Main_FAB_Left)

    }

    private fun initViews() {
        updateRooster()
        Main_FAB_Left.setOnClickListener { view ->
            moveLeft()
            Log.d("FAB", "Start pressed")
        }
        Main_FAB_Right.setOnClickListener { view ->
            moveRight()
            Log.d("FAB", "End pressed")
        }

    }

    //update rooster position in the Main Activity
    private fun updateRooster() {
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
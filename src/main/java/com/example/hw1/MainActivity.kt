package com.example.hw1

import android.content.Intent
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
import com.example.hw1.utilities.SignalManager
import com.example.hw1.utilities.SingleSoundPlayer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //Hearts
    private lateinit var Main_IMG_hearts: Array<AppCompatImageView>

    //Roosters

    private lateinit var Main_IMG_Rooster_1: AppCompatImageView

    private lateinit var Main_IMG_Rooster_2: AppCompatImageView

    private lateinit var Main_IMG_Rooster_3: AppCompatImageView

    private lateinit var Main_IMG_Rooster_4: AppCompatImageView

    private lateinit var Main_IMG_Rooster_5: AppCompatImageView

    private lateinit var Main_IMG_pans: Array<AppCompatImageView>

    private val isVisible = IntArray(30)

    //Fabs

    private lateinit var Main_FAB_Left: FloatingActionButton

    private lateinit var Main_FAB_Right: FloatingActionButton

    //Others


    private lateinit var Main_LBL_Score: MaterialTextView

    private var score = 0

    private var scoreFlag: Boolean = false


    private var roosterPosition: Int = 2 //0 - 4

    private lateinit var timerJob: Job //Timer For Coroutine

    private var spaceFlag: Int = 0 //Boolean For Spacing The Lines

    private var lastSpawnLane = -1

    private var sameLaneStreak = 0
    private var hits: Int = 0

    private var didNavigateToEndScreen = false
    private var isGameOver: Boolean = false


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

    private fun findViews() {

        Main_IMG_hearts = arrayOf(
            findViewById(R.id.Main_IMG_Heart_L),
            findViewById(R.id.Main_IMG_Heart_M),
            findViewById(R.id.Main_IMG_Heart_R)
        )
        Main_IMG_Rooster_1 = findViewById(R.id.Main_IMG_Rooster_1)
        Main_IMG_Rooster_2 = findViewById(R.id.Main_IMG_Rooster_2)
        Main_IMG_Rooster_3 = findViewById(R.id.Main_IMG_Rooster_3)
        Main_IMG_Rooster_4 = findViewById(R.id.Main_IMG_Rooster_4)
        Main_IMG_Rooster_5 = findViewById(R.id.Main_IMG_Rooster_5)

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
            findViewById(R.id.Main_IMG_Pan18),
            findViewById(R.id.Main_IMG_Pan19),
            findViewById(R.id.Main_IMG_Pan20),
            findViewById(R.id.Main_IMG_Pan21),
            findViewById(R.id.Main_IMG_Pan22),
            findViewById(R.id.Main_IMG_Pan23),
            findViewById(R.id.Main_IMG_Pan24),
            findViewById(R.id.Main_IMG_Pan25),
            findViewById(R.id.Main_IMG_Pan26),
            findViewById(R.id.Main_IMG_Pan27),
            findViewById(R.id.Main_IMG_Pan28),
            findViewById(R.id.Main_IMG_Pan29),
            findViewById(R.id.Main_IMG_Pan30)
        )

        Main_FAB_Right = findViewById(R.id.Main_FAB_Right)
        Main_FAB_Left = findViewById(R.id.Main_FAB_Left)

        Main_LBL_Score = findViewById(R.id.Main_LBL_Score)

    }

    private fun initViews() {
        updateRooster()
        Main_FAB_Left.setOnClickListener { view ->
            moveLeft()
        }
        Main_FAB_Right.setOnClickListener { view ->
            moveRight()
        }


    }

    // Start Timer Coroutine For Pans UI Update
    private fun startGame() {

        timerJob = lifecycleScope.launch {
            while (isActive) {
                Log.d("Timer Runnable", "Active: \$isActive")
                // Refresh UI:
                updatePanUI()
                delay(Constants.Timer.DELAY)
                if (scoreFlag)
                    updateScore()
                else
                    scoreFlag = !scoreFlag
                checkIfGameOver("Game Over!")
            }
        }
    }

    fun updateScore(){
        score ++
        Main_LBL_Score.text = String.format("%03d", score)
    }
    fun checkIfGameOver(message: String) {
        if (isGameOver && !didNavigateToEndScreen) {
            didNavigateToEndScreen = true
            timerJob.cancel()
            val intent = Intent(this, GameEndScreen::class.java)
            val bundle = Bundle()
            bundle.putString(Constants.BundleKeys.MESSAGE_KEY, message)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }

    // Choose random Pan To Add & Call For Func To - Update The Game Board, Check If Hit Happened
    private fun updatePanUI() {
        updateVisibleArray()
        if (spaceFlag == 0) {
            var lane = Random.nextInt(5)

            if (lane == lastSpawnLane) {
                sameLaneStreak++
                if (sameLaneStreak >= 2) {
                    lane = (0..2).filter { it != lastSpawnLane }.random()
                    sameLaneStreak = 0
                }
            } else {
                lastSpawnLane = lane
                sameLaneStreak = 0
            }

            isVisible[lane] = 1
            spaceFlag = 1
        } else
            spaceFlag = 0
        refreshUI()
        checkForHit()


    }

    //Change Visibility Of Pans And 'Move' them Down
    private fun refreshUI() {
        Main_IMG_pans.forEachIndexed { index, img ->
            if (isVisible[index] == 1) {
                img.visibility = View.VISIBLE
            } else
                img.visibility = View.INVISIBLE

        }
    }

    //Check If There Is A Collision Between A Rooster And A Pan
    private fun checkForHit() {
        val hitIndex = when (roosterPosition) {
            0 -> 25
            1 -> 26
            2 -> 27
            3 -> 28
            else -> 29
        }

        if (isVisible[hitIndex] == 1) {
            makeHit()
            isVisible[hitIndex] = 0
            refreshUI()
        }
    }

    //Calling a Function For Heart Decrease & Calling Functions For Toast & Vibrate
    private fun makeHit() {
        val ssp = SingleSoundPlayer(this)
        ssp.playSound(R.raw.hitsound)
        heartDecrease()
        makeToast()
        makeVibration()

    }

    //Heart Ui Update
    private fun heartDecrease() {
        hits++
        Main_IMG_hearts[Main_IMG_hearts.size - hits]
            .visibility = View.INVISIBLE

        if (hits == 3) {
            isGameOver = true
        }

    }

    //Toast Function
    private fun makeToast() {
        SignalManager
            .getInstance()
            .toast(
                "You've Been Hit!",
                SignalManager.ToastLength.Long
            )
    }

    //Vibrate Function
    private fun makeVibration() {
        SignalManager
            .getInstance()
            .vibrate()
    }

    //Update The Array That Track Visibility Of The Pans
    private fun updateVisibleArray() {
        Main_IMG_pans.forEachIndexed { index, img ->
            if (img.visibility == View.VISIBLE && index < 25) {
                isVisible[index] = 0
                isVisible[index + 5] = 1
            }
            if (img.visibility == View.VISIBLE && index >= 25) {
                isVisible[index] = 0
            }

        }
    }

    //update rooster position in the Main Activity
    private fun updateRooster() {
        Main_IMG_Rooster_1.visibility = View.INVISIBLE
        Main_IMG_Rooster_2.visibility = View.INVISIBLE
        Main_IMG_Rooster_3.visibility = View.INVISIBLE
        Main_IMG_Rooster_4.visibility = View.INVISIBLE
        Main_IMG_Rooster_5.visibility = View.INVISIBLE

        when (roosterPosition) {
            0 -> Main_IMG_Rooster_1.visibility = View.VISIBLE
            1 -> Main_IMG_Rooster_2.visibility = View.VISIBLE
            2 -> Main_IMG_Rooster_3.visibility = View.VISIBLE
            3 -> Main_IMG_Rooster_4.visibility = View.VISIBLE
            4 -> Main_IMG_Rooster_5.visibility = View.VISIBLE
        }
    }

    //updating the rooster position in the variable for right move
    private fun moveRight() {
        if (roosterPosition < 4) {
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


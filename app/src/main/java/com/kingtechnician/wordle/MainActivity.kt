package com.kingtechnician.wordle

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    // author: calren
    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }
    var wordToGuess= FourLetterWordList.getRandomFourLetterWord().lowercase()
    var guesses = 3
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
    private fun setCorrectionColors(buttonArray:Array<Button>,guessResults:String)
    {
        for (i in 0..guessResults.length-1)
        {
            if(guessResults.get(i).toString().equals("O"))
            {
                buttonArray[i].setBackgroundColor(Color.GREEN)
            }
            else if (guessResults.get(i).toString().equals("+"))
            {
                buttonArray[i].setBackgroundColor(Color.YELLOW)
            }
            else
            {
                buttonArray[i].setBackgroundColor(Color.RED)
            }
        }
    }
    private fun resetProcedure(answer:String,checkButton:Button,buttonArray:Array<Button>,input:EditText)
    {
        var correct = false
        var index = 0
        if(answer=="OOOO")
        {
            correct = true
        }
        if(guesses>0&&!correct)
        {
            return
        }
        else
        {
            setCorrectionColors(buttonArray,wordToGuess)
        }
        if(correct)
        {
            Toast.makeText(this,"Congrats for getting the word correct!",Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(this,"Sorry, the word was: $wordToGuess",Toast.LENGTH_LONG).show()
        }
        guesses=3
        for (b in buttonArray)
        {
            b.setBackgroundColor(Color.WHITE)
            b.text = "|"
        }
        checkButton.text = "Guesses Remaining: $guesses"
        wordToGuess=FourLetterWordList.getRandomFourLetterWord().lowercase()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var input = findViewById<EditText>(R.id.word)
        var view = findViewById<ConstraintLayout>(R.id.constraintView)
        var buttonArray = arrayOf(findViewById<Button>(R.id.button1),findViewById<Button>(R.id.button2),findViewById<Button>(R.id.button3),findViewById<Button>(R.id.button4))

        for (b in buttonArray)
        {
            b.text="|"
        }
        var checkButton = findViewById<Button>(R.id.checkButton)
        checkButton.setOnClickListener()
        {
            if(input.text.length==4 && guesses>0)
            {
                var answer = checkGuess(input.text.toString())
                println("Answer: $answer")

                if(guesses>0)
                {
                    guesses -= 1
                }
                setCorrectionColors(buttonArray,answer)
                resetProcedure(answer,checkButton,buttonArray,input)
                checkButton.text = "Guesses Remaining: $guesses"
                if (guesses<=0)
                {

                    Toast.makeText(it.context,"Game will end here",Toast.LENGTH_LONG).show()

                }
            }
            else if(guesses==0&&input.text.length==4)
            {
                resetProcedure(checkGuess(input.text.toString()),checkButton,buttonArray,input)
            }
        }
        input.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s:CharSequence,start:Int,count:Int,after:Int)
            {
                var displayList = s.toString()
                var length = displayList.length
                var counter =0
                for (i in 0..3)
                {
                    if(i<length)
                    {
                        buttonArray[i].text = displayList[i].toString()
                    }
                    else
                    {
                        buttonArray[i].text = "|"
                    }
                }
            }
        })

    }
}
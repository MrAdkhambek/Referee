package r2.llc.referee

import com.github.terrakok.cicerone.androidx.FragmentScreen
import r2.llc.referee.main.MainFragment
import r2.llc.referee.result.ResultFragment

object Screens {

    fun Main(): FragmentScreen = FragmentScreen {
        MainFragment()
    }

    fun Result(winner: String): FragmentScreen = FragmentScreen {
        ResultFragment.newInstance(winner)
    }
}
package r2.llc.referee

import com.github.terrakok.cicerone.androidx.FragmentScreen
import r2.llc.referee.main.MainFragment

object Screens {

    fun Main(): FragmentScreen = FragmentScreen {
        MainFragment()
    }
}
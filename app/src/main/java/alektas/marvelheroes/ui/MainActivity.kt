package alektas.marvelheroes.ui

import alektas.marvelheroes.R
import alektas.marvelheroes.ui.heroes.HeroesFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HeroesFragment.newInstance())
                .commitNow()
        }
    }
}
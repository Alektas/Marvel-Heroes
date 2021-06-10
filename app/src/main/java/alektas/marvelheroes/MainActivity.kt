package alektas.marvelheroes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import alektas.marvelheroes.ui.searchlist.SearchFragment

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
    }
}
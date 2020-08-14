package app.rss.ua.rsswsj.view.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import android.view.MenuItem
import app.rss.ua.rsswsj.R
import app.rss.ua.rsswsj.view.fragment.tabs.TimeFragment
import app.rss.ua.rsswsj.view.fragment.tabs.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author OTarasiuk
 * @since 13.08.2020
 */
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        Log.d(this.localClassName, "init" +this.localClassName)
    }

    private fun initViews() {
        loadFragment(TimeFragment())
        bottomNavigation.setOnNavigationItemSelectedListener (this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        when (item.itemId) {
            R.id.navigation_tab_1 -> {
                supportActionBar?.title = getString(R.string.tab_1)
                fragment = TimeFragment()
                loadFragment(fragment)
                return true
            }
            R.id.navigation_tab_2 -> {
                supportActionBar?.title = getString(R.string.tab_2)
                fragment = NewsFragment()
                loadFragment(fragment)
                return true
            }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        Log.d(this.localClassName, "loading fragment manager" +this.localClassName)
        val transaction = supportFragmentManager.beginTransaction()
        with(transaction) {
            replace(R.id.frame_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}

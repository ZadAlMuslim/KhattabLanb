
package com.airsoft.khattablan

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, MainFragment())
            .commit()
    }
}

class MainFragment : BrowseSupportFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        title = "KhattabLan TV"
        
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = rowsAdapter

        // Add app rows (categories)
        createAppsRow(rowsAdapter)
        createMediaRow(rowsAdapter)
        createSettingsRow(rowsAdapter)
    }

    private fun createAppsRow(rowsAdapter: ArrayObjectAdapter) {
        val appsAdapter = ArrayObjectAdapter(CardPresenter())
        // Add your installed apps here
        
        val header = HeaderItem(0, "Apps")
        rowsAdapter.add(ListRow(header, appsAdapter))
    }

    private fun createMediaRow(rowsAdapter: ArrayObjectAdapter) {
        val mediaAdapter = ArrayObjectAdapter(CardPresenter())
        // Add media items like YouTube, Netflix etc
        
        val header = HeaderItem(1, "Media")
        rowsAdapter.add(ListRow(header, mediaAdapter))
    }

    private fun createSettingsRow(rowsAdapter: ArrayObjectAdapter) {
        val settingsAdapter = ArrayObjectAdapter(CardPresenter())
        // Add settings items
        
        val header = HeaderItem(2, "Settings")
        rowsAdapter.add(ListRow(header, settingsAdapter))
    }
}

class CardPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val view = ImageCardView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val cardView = viewHolder.view as ImageCardView
        // Bind your data to the card here
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}

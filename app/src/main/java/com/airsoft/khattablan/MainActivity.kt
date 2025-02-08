
package com.airsoft.khattablan

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.bumptech.glide.Glide

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, MainFragment())
            .commit()
    }
}

data class App(
    val name: String,
    val packageName: String,
    val iconResId: Int = 0
)

class MainFragment : BrowseSupportFragment() {
    private val COPYRIGHT = "© 2024 KhattabLan TV - By Khattab"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        title = "KhattabLan TV"
        brandColor = resources.getColor(android.R.color.holo_blue_dark)
        searchAffordanceColor = resources.getColor(android.R.color.holo_orange_dark)
        
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = rowsAdapter

        createAppsRow(rowsAdapter)
        createMediaRow(rowsAdapter)
        createSettingsRow(rowsAdapter)
        
        setOnSearchClickedListener {
            // Handle search
        }
        
        setOnItemViewClickedListener { _, item, _, _ ->
            when (item) {
                is App -> launchApp(item.packageName)
            }
        }
        
        // Show copyright in footer
        setTitle(title.toString() + "\n" + COPYRIGHT)
    }
    
    private fun launchApp(packageName: String) {
        try {
            val intent = requireActivity().packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                startActivity(intent)
            }
        } catch (e: Exception) {
            // Handle error
        }
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
        val cardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setMainImageDimensions(176, 176)
        }
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any) {
        val cardView = viewHolder.view as ImageCardView
        when (item) {
            is App -> {
                cardView.titleText = item.name
                if (item.iconResId != 0) {
                    Glide.with(cardView.context)
                        .load(item.iconResId)
                        .into(cardView.mainImageView)
                }
            }
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        val cardView = viewHolder.view as ImageCardView
        cardView.badgeImage = null
        cardView.mainImage = null
    }
}

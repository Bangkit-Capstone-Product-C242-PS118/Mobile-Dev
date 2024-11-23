package com.capstone.pantauharga.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.pantauharga.R
import com.capstone.pantauharga.database.FavoriteEvents
import com.capstone.pantauharga.database.FavoriteRoomDatabase
import com.capstone.pantauharga.databinding.ActivityDetailEventBinding
import com.capstone.pantauharga.repository.EventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private lateinit var viewModel: DetailEventViewModel
    private var isFavorite = false

    companion object {
        const val EVENT_ID_KEY = "EVENT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = FavoriteRoomDatabase.getDatabase(this)
        val repository = EventsRepository(database.favoriteEventsDao())

        val factory = DetailEventViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DetailEventViewModel::class.java]

        val eventId = intent.getStringExtra(EVENT_ID_KEY) ?: return
        viewModel.fetchEventDetail(eventId)

        viewModel.eventDetail.observe(this) { detailResponse ->
            detailResponse.event.let { event ->
                with(binding) {
                    tvEventName.text = event.name
                    val remainingQuota = event.quota - event.registrants
                    tvQuota.text = getString(R.string.remaining_quota, remainingQuota)
                    tvBeginTime.text = getString(R.string.begin_time, event.beginTime)
                    tvShow.text = getString(R.string.organizer, event.ownerName)
                    tvDescription.text = HtmlCompat.fromHtml(
                        event.description,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )

                    Glide.with(this@DetailEventActivity)
                        .load(event.imageLogo)
                        .into(imageViewLogo)

                    viewModel.getFavoriteEventById(event.id.toString()).observe(this@DetailEventActivity) { favoriteEvent ->
                        isFavorite = favoriteEvent != null
                        favButton.setImageResource(if (isFavorite) R.drawable.ic_fav_full else R.drawable.ic_fav_border)
                    }

                    favButton.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            if (isFavorite) {
                                viewModel.deleteFavoriteEvent(FavoriteEvents(event.id.toString(), event.name, event.imageLogo))
                                isFavorite = false
                                runOnUiThread {
                                    favButton.setImageResource(R.drawable.ic_fav_border)
                                    Toast.makeText(this@DetailEventActivity, "Removed from Favorites", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                val newFavoriteEvent = FavoriteEvents(event.id.toString(), event.name, event.imageLogo)
                                viewModel.insertFavoriteEvent(newFavoriteEvent)
                                isFavorite = true
                                runOnUiThread {
                                    favButton.setImageResource(R.drawable.ic_fav_full)
                                    Toast.makeText(this@DetailEventActivity, "Added to Favorites", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    btnRegister.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                        startActivity(intent)
                    }
                }
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.error.observe(this) { isError ->
            if (isError) {
                Toast.makeText(this, "Failed to load Data", Toast.LENGTH_SHORT).show()
                viewModel.setError(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
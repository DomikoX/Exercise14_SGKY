package sk.hyll.dominik.exercise14_sgky

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide

import org.json.JSONException
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        try {
            val jsonObject = JSONObject(intent.getStringExtra("course"))
            supportActionBar!!.title = "SGKY:" + jsonObject.getString("course")
            val courseImageView = findViewById(R.id.courseImageView) as ImageView
            Glide.with(this).load("http://ptm.fi/jamk/android/golfcourses/" + jsonObject.getString("image")).into(courseImageView)
            val courseAddressTextView = findViewById(R.id.courseAddressTextView) as TextView
            courseAddressTextView.text = jsonObject.getString("address")
            val coursePhoneTextView = findViewById(R.id.coursePhoneTextView) as TextView
            coursePhoneTextView.text = jsonObject.getString("phone")
            val courseEmailTextView = findViewById(R.id.courseEmailTextView) as TextView
            courseEmailTextView.text = jsonObject.getString("email")
            val courseInfoTextView = findViewById(R.id.courseInfoTextView) as TextView
            courseInfoTextView.text = jsonObject.getString("text")
            val courseWebTextView = findViewById(R.id.courseWebTextView) as TextView
            courseWebTextView.text = jsonObject.getString("web")
            val courseMapTextView = findViewById(R.id.courseMapTextView) as TextView
            courseMapTextView.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            courseMapTextView.text = "Näytä kartalla"
            courseMapTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                try {
                    val lat = jsonObject.getString("lat")
                    val lng = jsonObject.getString("lng")
                    val course = jsonObject.getString("course")
                    intent.data = Uri.parse("geo:<$lat>,<$lng>?q=<$lat>,<$lng>($course)")
                    startActivity(intent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
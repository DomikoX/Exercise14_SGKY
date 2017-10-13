package sk.hyll.dominik.exercise14_sgky

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CourseAdapter(private val mCourses: JSONArray, private val mContext: Context) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return mCourses.length()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            val jsonObject = mCourses.getJSONObject(position)
            // images are in ptm.fi web server
            Glide.with(mContext).load("http://ptm.fi/jamk/android/golfcourses/" + jsonObject.getString("image")).into(viewHolder.courseImageView)
            viewHolder.courseNameTextView.text = jsonObject.getString("course")
            viewHolder.courseAddressTextView.text = jsonObject.getString("address")
            viewHolder.coursePhoneTextView.text = jsonObject.getString("phone")
            viewHolder.courseEmailTextView.text = jsonObject.getString("email")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var courseImageView: ImageView
        var courseNameTextView: TextView
        var courseAddressTextView: TextView
        var coursePhoneTextView: TextView
        var courseEmailTextView: TextView

        init {
            courseImageView = itemView.findViewById(R.id.courseImageView) as ImageView
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView) as TextView
            courseAddressTextView = itemView.findViewById(R.id.courseAddressTextView) as TextView
            coursePhoneTextView = itemView.findViewById(R.id.coursePhoneTextView) as TextView
            courseEmailTextView = itemView.findViewById(R.id.courseEmailTextView) as TextView
            // item click handler
            itemView.setOnClickListener {
                val position = adapterPosition
                try {
                    val jsonObject = mCourses.getJSONObject(position)
                    // open DetailActivity
                    val intent = Intent(mContext, DetailActivity::class.java)
                    intent.putExtra("course", jsonObject.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext.startActivity(intent)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

}
package sk.hyll.dominik.exercise14_sgky

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONException
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import android.support.design.widget.CollapsingToolbarLayout
import android.util.Log


class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set main layout
        setContentView(R.layout.activity_main)

        // set toolbar title
        val collapsingToolbarLayout = findViewById(R.id.main_collapsing) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = "SGKY - Kentät"

        // define LoadJSONTask object
        val loadJSONTask = LoadJSONTask()
        // define listener
        loadJSONTask.setListener(object : LoadJSONTask.LoadJSONTaskListener {
            override fun onPostExecuteConcluded(json: JSONObject?) {
                if (json == null) {
                    Log.d("JSON", "MainActivity coursesLoaded called. Courses == null!")
                } else {
                    try {
                        val courses = json.getJSONArray("courses")
                        Log.d("JSON", "MainActivity coursesLoaded called. Courses length = " + courses.length())
                        mRecyclerView = findViewById(R.id.courseRecyclerView) as RecyclerView
                        mLayoutManager = LinearLayoutManager(this@MainActivity)
                        mRecyclerView!!.layoutManager = mLayoutManager
                        mAdapter = CourseAdapter(courses, applicationContext)
                        mRecyclerView!!.adapter = mAdapter
                    } catch (e: JSONException) {
                        Log.e("JSON", "Error getting courses data.")
                    }

                }
            }
        })
        // start loading JSON data
        loadJSONTask.execute("http://ptm.fi/materials/golfcourses/golf_courses.json")
    }
}

package sk.hyll.dominik.exercise14_sgky

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class LoadJSONTask : AsyncTask<String, Void, JSONObject?>() {

    // define callback listener
    private var mListener: LoadJSONTaskListener? = null

    interface LoadJSONTaskListener {
        fun onPostExecuteConcluded(result: JSONObject?)
    }

    fun setListener(listener: LoadJSONTaskListener) {
        mListener = listener
    }

    override fun doInBackground(vararg urls: String): JSONObject? {
        var urlConnection: HttpURLConnection? = null
        var json: JSONObject? = null
        try {
            val url = URL(urls[0])
            urlConnection = url.openConnection() as HttpURLConnection
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val stringBuilder = StringBuilder()

            var line = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line).append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()
            json = JSONObject(stringBuilder.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            if (urlConnection != null) urlConnection.disconnect()
        }
        return json
    }

    override fun onPostExecute(json: JSONObject?) {
        if (mListener != null) mListener!!.onPostExecuteConcluded(json)
    }

}

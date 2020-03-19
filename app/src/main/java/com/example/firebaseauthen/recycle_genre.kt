package com.example.firebaseauthen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray


class recycle_genre(context: Context, val dataSource: JSONArray) : RecyclerView.Adapter<recycle_genre.Holder>() {
    private val thiscontext : Context = context

    class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val View = view

        lateinit var layout : LinearLayout
        lateinit var movie_title: TextView
        lateinit var movie_duration: TextView
        lateinit var movie_rating: TextView
        lateinit var movie_plot: TextView
        lateinit var movie_thumb_nail: ImageView

        fun Holder(){
            layout = View.findViewById<View>(R.id.recy_genre) as LinearLayout
            movie_title = View.findViewById<View>(R.id.movie_title) as TextView
            movie_duration = View.findViewById<View>(R.id.movie_duration) as TextView
            movie_rating = View.findViewById<View>(R.id.movie_rating) as TextView
            movie_plot = View.findViewById<View>(R.id.movie_plot) as TextView
            movie_thumb_nail = View.findViewById<View>(R.id.movie_thumb_nail) as ImageView
        }

    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recycle_genre, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSource.length()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var movie_title:String = dataSource.getJSONObject(position).getString("title").toString()
        var movie_duration:String = dataSource.getJSONObject(position).getString("duration").toString()
        var movie_rating:String = dataSource.getJSONObject(position).getString("imdbRating").toString()
        var movie_plot:String = dataSource.getJSONObject(position).getString("storyline").toString()
        var movie_thumb_nail:String = dataSource.getJSONObject(position).getString("posterurl").toString()

        holder.Holder()
        holder.movie_title.setText(movie_title)
        holder.movie_duration.setText(movie_duration+"in")
        holder.movie_rating.setText(movie_rating)
        holder.movie_plot.setText("\t\t\t" + movie_plot)

        Glide.with(thiscontext)
            .load(movie_thumb_nail)
            .into(holder.movie_thumb_nail)

        holder.layout.setOnClickListener{
            val movieDetail: Fragment = Movie_detail().newInstance(movie_title,movie_duration,movie_rating,movie_plot,movie_thumb_nail)
            val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right)
            transaction.replace(R.id.layout, movieDetail)
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.commit()
            Toast.makeText(thiscontext,holder.movie_title.text.toString(), Toast.LENGTH_SHORT).show()
        }

//        holder.layout.setOnLongClickListener{
//            Toast.makeText(thiscontext, "Long click detected", Toast.LENGTH_SHORT).show()
//            true
//        }

    }

}

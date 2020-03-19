package com.example.firebaseauthen


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 */
class Movie_detail : Fragment() {
    private var detail_title:String?=null
    private var detail_duration:String?=null
    private var detail_rating:String?=null
    private var detail_plot:String?=null
    private var detail_img:String?=null

    fun newInstance(movie_title: String,movie_duration:String,movie_rating:String,movie_plot:String,movie_thumb_nail:String): Movie_detail {
        val fragment = Movie_detail()
        val bundle = Bundle()
        bundle.putString("detail_title", movie_title)
        bundle.putString("detail_duration", movie_duration)
        bundle.putString("detail_rating", movie_rating)
        bundle.putString("detail_plot", movie_plot)
        bundle.putString("detail_img", movie_thumb_nail)
        fragment.setArguments(bundle)
        return fragment
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        val title : TextView = view.findViewById(R.id.detail_title)
        val duration : TextView = view.findViewById(R.id.detail_duration)
        val rating : TextView = view.findViewById(R.id.detail_rating)
        val plot : TextView = view.findViewById(R.id.detail_plot)
        val img : ImageView = view.findViewById(R.id.detail_img)
        title.text = this.detail_title
        duration.text = this.detail_duration+"in"
        rating.text = this.detail_rating
        plot.text = "\t\t\t"+this.detail_plot
        Glide.with(activity!!.baseContext)
            .load(detail_img)
            .into(img)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            detail_title = bundle.getString("detail_title").toString()
            detail_duration = bundle.getString("detail_duration").toString()
            detail_rating = bundle.getString("detail_rating").toString()
            detail_plot = bundle.getString("detail_plot").toString()
            detail_img = bundle.getString("detail_img").toString()
        }
    }

}
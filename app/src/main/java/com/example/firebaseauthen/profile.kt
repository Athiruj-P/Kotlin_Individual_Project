package com.example.firebaseauthen


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONArray
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class profile : Fragment() {

    var PhotoURL: String = ""
    var Name: String = ""

    fun newInstance(url: String, name: String): profile {

        val profile = profile()
        val bundle = Bundle()
        bundle.putString("PhotoURL", url)
        bundle.putString("Name", name)
        profile.setArguments(bundle)

        return profile
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val mRootRef = FirebaseDatabase.getInstance().reference
        val inTheater = mRootRef.child("theater")
        val top = mRootRef.child("top02")

        var list01: JSONArray
        var list02: JSONArray
        inTheater.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val recycle_popular: RecyclerView = view.findViewById(R.id.recycle_popular)
                val layoutManager_popular: RecyclerView.LayoutManager = LinearLayoutManager(
                    activity!!.baseContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                recycle_popular.layoutManager = layoutManager_popular

                list01 = prepare_data(dataSnapshot)

                val adapter_popular = recycle_popular_adapter(activity!!.baseContext, list01)
                recycle_popular.adapter = adapter_popular
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        top.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val recycle_genre: RecyclerView = view.findViewById(R.id.recycle_genre_view)
                val layoutManager_genre: RecyclerView.LayoutManager = LinearLayoutManager(
                    activity!!.baseContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                recycle_genre.layoutManager = layoutManager_genre

                val recycle_genre_action: RecyclerView =
                    view.findViewById(R.id.recycle_genre_action)
                val layoutManager_genre_action: RecyclerView.LayoutManager = LinearLayoutManager(
                    activity!!.baseContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                recycle_genre_action.layoutManager = layoutManager_genre_action

                list02 = prepare_data(dataSnapshot)

                val adapter_genre = recycle_genre(activity!!.baseContext, list02)
                recycle_genre.adapter = adapter_genre

                val adapter_genre_action = recycle_genre(activity!!.baseContext, list02)
                recycle_genre_action.adapter = adapter_genre_action
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        val ivProfilePicture = view.findViewById(R.id.iv_profile) as ImageView
        val fb_name = view.findViewById(R.id.fb_name) as TextView
        val login_button2 = view.findViewById(R.id.login_button2) as Button

        Glide.with(activity!!.baseContext)
            .load(PhotoURL)
            .into(ivProfilePicture)

        fb_name.setText(Name)

        login_button2.setOnClickListener {
            LoginManager.getInstance().logOut()
            activity!!.supportFragmentManager.popBackStack()
        }

        val report_button = view.findViewById(R.id.report_button) as Button
        report_button.setOnClickListener {
            val reportFrag: Fragment = report().newInstance(Name)
            val manager = (context as FragmentActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right
            )
            transaction.replace(R.id.layout, reportFrag, "report_fragment")
            transaction.addToBackStack("report_fragment")
            transaction.commit()
            Toast.makeText(context, "Report", Toast.LENGTH_SHORT).show()
        }

        val stat_button = view.findViewById(R.id.stat_button) as Button
        stat_button.setOnClickListener {
            val statFrag: Fragment = stat()
            val manager = (context as FragmentActivity).supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.enter_right_to_left,
                R.anim.exit_right_to_left,
                R.anim.enter_left_to_right,
                R.anim.exit_left_to_right
            )
            transaction.replace(R.id.layout, statFrag, "stat_fragment")
            transaction.addToBackStack("stat_fragment")
            transaction.commit()
            Toast.makeText(context, "Statistics", Toast.LENGTH_SHORT).show()
        }

        return view

    }

    fun prepare_data(dataSnapshot: DataSnapshot): JSONArray {
        val list = JSONArray()
        for (ds in dataSnapshot.children) {
            val jObject = JSONObject()

            val title = ds.child("title").getValue(String::class.java)!!
            val duration = ds.child("duration").getValue(String::class.java)!!
            val imdbRating = ds.child("imdbRating").getValue(String::class.java)!!
            val storyline = ds.child("storyline").getValue(String::class.java)!!
            val posterurl = ds.child("posterurl").getValue(String::class.java)!!

            jObject.put("key", ds.key)
            jObject.put("title", title)
            jObject.put("duration", duration)
            jObject.put("imdbRating", imdbRating)
            jObject.put("storyline", storyline)
            jObject.put("posterurl", posterurl)

            list.put(jObject)
        }
        return list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            PhotoURL = bundle.getString("PhotoURL").toString()
            Name = bundle.getString("Name").toString()

        }

    }

}


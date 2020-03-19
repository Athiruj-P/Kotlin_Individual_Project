package com.example.firebaseauthen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
class report : Fragment() {
    private var fb_name: String? = null

    fun newInstance(fbName: String): report {
        val fragment = report()
        val bundle = Bundle()
        bundle.putString("fbName", fbName)
        fragment.setArguments(bundle)
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_report, container, false)

        val report_button = view.findViewById<Button>(R.id.report_button)


        val mRootRef = FirebaseDatabase.getInstance().getReference()
        val mMessagesRef = mRootRef.child("messages")

        report_button.setOnClickListener {
            val feedback_text = view.findViewById<EditText>(R.id.feedback_text).text.toString()
            val key = mMessagesRef.push().key
            val postValues: HashMap<String, Any> = HashMap()
            postValues["username"] = fb_name.toString()
            postValues["text"] = feedback_text
            val childUpdates: MutableMap<String, Any> = HashMap()
            childUpdates["/user-messages/$fb_name/$key"] = postValues
            mMessagesRef.updateChildren(childUpdates)
            Toast.makeText(context,"Thank you for your beedback", Toast.LENGTH_SHORT).show()
            fragmentManager!!.popBackStackImmediate()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        if (bundle != null) {
            fb_name = bundle.getString("fbName").toString()
        }
    }

}

package com.atilsamancioglu.fotografpaylasmauygulamasi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.atilsamancioglu.fotografpaylasmauygulamasi.adapter.FeedRecyclerAdapter
import com.atilsamancioglu.fotografpaylasmauygulamasi.model.Post
import com.atilsamancioglu.fotografpaylasmauygulamasi.R
import com.atilsamancioglu.fotografpaylasmauygulamasi.databinding.FragmentFeedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FeedFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    var adapter : FeedRecyclerAdapter? = null
    val postArrayList : ArrayList<Post> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.floatingActionButton.setOnClickListener { addButtonClicked(it) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        getDataFromFirestore()

        adapter = FeedRecyclerAdapter(postArrayList)
        binding.recyclerView.adapter = adapter

    }

    fun addButtonClicked(view: View){
        val popup = PopupMenu(requireContext(), binding.floatingActionButton)
        val inflater  = popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu,popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    fun getDataFromFirestore() {

        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                } else {

                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {

                            postArrayList.clear()

                            val documents = snapshot.documents
                            for (document in documents) {
                                val comment = document.get("comment") as String
                                val useremail = document.get("userEmail") as String
                                val downloadUrl = document.get("downloadUrl") as String
                                //val timestamp = document.get("date") as Timestamp
                                //val date = timestamp.toDate()

                                val post = Post(useremail, comment, downloadUrl)
                                postArrayList.add(post)
                            }
                            adapter!!.notifyDataSetChanged()

                        }
                    }

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.uploadMenu) {
            val action = FeedFragmentDirections.actionFeedFragmentToAddFragment()
            Navigation.findNavController(requireView()).navigate(action)

        } else if (item.itemId == R.id.logoutMenu) {
            auth.signOut()
            val action = FeedFragmentDirections.actionFeedFragmentToSignupFragment()
            Navigation.findNavController(requireView()).navigate(action)

        }
        return true
    }
}
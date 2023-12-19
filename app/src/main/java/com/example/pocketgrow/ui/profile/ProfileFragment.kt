package com.example.pocketgrow.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import com.example.pocketgrow.R
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pocketgrow.ViewModelFactory
import com.example.pocketgrow.helper.AuthPreference
import com.example.pocketgrow.login.LoginActivity
import com.example.pocketgrow.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private lateinit var profileImageView: ImageView
    private lateinit var logoutButton: LinearLayout
    private lateinit var emailTextView: TextView
    private lateinit var nameTextView: TextView


    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = requireContext()
        val authPreference = AuthPreference(context)
        val email = authPreference.getValue("email")
        val fullname = authPreference.getValue("fullname")

        System.out.println("email")
        System.out.println(email)

        System.out.println("fullname")
        System.out.println(fullname)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileImageView = view.findViewById(R.id.profileImageView)
        logoutButton = view.findViewById(R.id.logoutButton)

        emailTextView = view.findViewById(R.id.tvemail)
        emailTextView.setText(email)

        nameTextView = view.findViewById(R.id.tvname)
        nameTextView.setText(fullname)

        setupAction()

        return view
    }

    private fun setupAction() {
        logoutButton.setOnClickListener {
            // Membuat AlertDialog
            AlertDialog.Builder(requireContext())
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    // Melakukan logout
                    loginViewModel.logout()

                    // Navigasi ke LoginActivity
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    activity?.finish()
                }
                .setNegativeButton("No") { dialog, _ ->
                    // Batal logout, tutup AlertDialog
                    dialog.dismiss()
                }
                .show()
        }
    }
}
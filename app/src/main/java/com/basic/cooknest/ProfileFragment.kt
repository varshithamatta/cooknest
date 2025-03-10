package com.basic.cooknest

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.basic.cooknest.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        if (user != null) {
            if (user.providerData.any { it.providerId == "google.com" }) {
                // User signed in via Google
                binding.profileName.text = user.displayName
                binding.profileEmail.text = user.email
                Glide.with(this).load(user.photoUrl).into(binding.profileImage)
            } else {
                // User signed in via Email & Password
                val storedName = sharedPreferences.getString("userName", "No Name")
                binding.profileName.text = storedName
                binding.profileEmail.text = user.email
                binding.profileImage.setImageResource(R.drawable.profile) // A default profile image
            }
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }
    }
}
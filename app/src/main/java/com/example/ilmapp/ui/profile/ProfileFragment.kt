package com.example.ilmapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ilmapp.R
import com.example.ilmapp.data.model.AuthViewModel
import com.example.ilmapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    val profileDetails = listOf(
        Triple(R.drawable.edit_icon,"Edit", "Your Personal Information"),
        Triple(R.drawable.email_listv, "Email", "Personal Email: michael@example.com"),
        Triple(R.drawable.call2_icon, "Phone Number","Mobile: (209) 555-0104"),
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val btnSettings = binding.btnSettings


        authViewModel.getUserProfile(2)
        Log.e("ProfileFragment", "ÇIKTI")


        val adapter = object : ArrayAdapter<Triple<Int, String, String>>(
            requireContext(),
            R.layout.profile_list_item,
            profileDetails
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.profile_list_item, parent, false)
                val (iconRes, textInfo, textDetail) = getItem(position)!!

                val icon = view.findViewById<ImageView>(R.id.pfIcon)
                val info = view.findViewById<TextView>(R.id.txtPfInfo)
                val detail = view.findViewById<TextView>(R.id.txtPfDetail)

                icon.setImageResource(iconRes)
                info.text = textInfo
                detail.text = textDetail
                return view
            }

        }
        val listView: ListView = binding.lvProfileItems
        listView.adapter = adapter



        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_settings)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
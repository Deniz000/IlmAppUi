package com.example.ilmapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ilmapp.R
import com.example.ilmapp.config.PreferencesManager.getUserData
import com.example.ilmapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileDetails = listOf(
        ProfileItem(R.drawable.edit_icon, "Edit", "Your Personal Information"),
        ProfileItem(R.drawable.email_listv, "Email", "michael@example.com"),
        ProfileItem(R.drawable.call2_icon, "Phone Number", "+90 (209) 555-0104"),
        ProfileItem(R.drawable.logout, "Log out", "")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupProfileList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProfileInfo()
    }

    override fun onResume() {
        super.onResume()
        setupProfileInfo()
    }

    private fun setupProfileInfo() {
        getUserData(requireContext()).let { (username, email, _) ->
            binding.profileName.text = username
            binding.profileEmail.text = email
        }
    }

    private fun setupProfileList() {
        val adapter = ProfileAdapter(profileDetails)
        binding.lvProfileItems.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    data class ProfileItem(val iconRes: Int, val title: String, val detail: String)

    private inner class ProfileAdapter(
        items: List<ProfileItem>
    ) : ArrayAdapter<ProfileItem>(requireContext(), R.layout.profile_list_item, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.profile_list_item, parent, false)
            val currentItem = getItem(position) ?: return view

            val icon = view.findViewById<ImageView>(R.id.pfIcon)
            val title = view.findViewById<TextView>(R.id.txtPfInfo)
            val detail = view.findViewById<TextView>(R.id.txtPfDetail)

            icon.setImageResource(currentItem.iconRes)
            title.text = currentItem.title
            detail.text = currentItem.detail

            view.setOnClickListener { handleItemClick(position) }
            return view
        }

        private fun handleItemClick(position: Int) {
            when (position) {
                0 -> findNavController().navigate(R.id.navigation_settings)
                3 -> findNavController().navigate(R.id.loginFragment)
            }
        }
    }
}

package com.example.ilmapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ilmapp.R
import com.example.ilmapp.config.PreferencesManager.getUserData
import com.example.ilmapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    val profileDetails = listOf(
        Triple(R.drawable.edit_icon,"Edit", "Your Personal Information"),
        Triple(R.drawable.email_listv, "Email", "michael@example.com"),
        Triple(R.drawable.call2_icon, "Phone Number","+90 (209) 555-0104"),
        Triple(R.drawable.logout, "Log out",""),
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val profileName = binding.profileName
        val profileEmail = binding.profileEmail

         getUserData(requireContext()).let { (username, email, _) ->
             profileName.text = username
             profileEmail.text = email
         }

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
                view.setOnClickListener {
                    when (position) {
                        0 ->findNavController().navigate(R.id.navigation_settings)
                        3 ->findNavController().navigate(R.id.loginFragment)
                        else -> null
                    }
                }
                return view
            }

        }
        val listView: ListView = binding.lvProfileItems
        listView.adapter = adapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
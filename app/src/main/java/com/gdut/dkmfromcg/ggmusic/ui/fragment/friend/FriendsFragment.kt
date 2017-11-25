package com.gdut.dkmfromcg.ggmusic.ui.fragment.friend


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gdut.dkmfromcg.ggmusic.R


/**
 * A simple [Fragment] subclass.
 */
class FriendsFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_friends, container, false)
    }

}// Required empty public constructor

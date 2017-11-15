package com.neiljaywarner.fdlopenmoduletest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neiljaywarner.fdlopenmoduletest.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_module_detail.*
import kotlinx.android.synthetic.main.module_detail.view.*

/**
 * A fragment representing a single Module detail screen.
 * This fragment is either contained in a [ModuleListActivity]
 * in two-pane mode (on tablets) or a [ModuleDetailActivity]
 * on handsets.
 */
class ModuleDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments.containsKey(ARG_ITEM_ID)) {
            Log.d("NJW", "containkey...")
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP[arguments.getString(ARG_ITEM_ID)]
            mItem?.let {
                activity.toolbar_layout?.title = it.content
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.module_detail, container, false)

        // Show the dummy content as text in a TextView.
        mItem?.let {
            rootView.module_detail.text = it.details
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}

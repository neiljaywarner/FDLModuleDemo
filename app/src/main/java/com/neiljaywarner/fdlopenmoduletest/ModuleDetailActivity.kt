package com.neiljaywarner.fdlopenmoduletest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import kotlinx.android.synthetic.main.activity_module_detail.*

/**
 * An activity representing a single Module detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ModuleListActivity].
 */
class ModuleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_detail)
        setSupportActionBar(detail_toolbar)

        handleDynamicLink()


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val arguments = Bundle()
            arguments.putString(ModuleDetailFragment.ARG_ITEM_ID,
                    intent.getStringExtra(ModuleDetailFragment.ARG_ITEM_ID))
            val fragment = ModuleDetailFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.module_detail_container, fragment)
                    .commit()
        }
    }


    private fun handleDynamicLink() {
        Log.i("NJW", "handleDynamicLink method")
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                    Log.i("NJW", "onSuccessListener ")

                    // Get deep link from result (may be null if no link is found)
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                    }
                    else {
                        Log.i("NJW", "pendingDynamicLinkData = null")

                    }
                    handleDeepLink(deepLink)

                    // Handle the deep link. For example, open the linked
                    // content, or apply promotional credit to the user's
                    // account.
                    // ...

                    // ...
                }
                .addOnFailureListener(this) { e -> Log.w("NJW", "getDynamicLink:onFailure", e) }
    }

    //e.g. https://www.mybswhealth.com/Providers/Detail/1871796078?srcCityZip%3DAddison
    private fun handleDeepLink(deepLink: Uri?) {
        Log.d("NJW", "handleDeepLink method with param: $deepLink")

        deepLink?.let {
            Log.d("NJW", "deepLink: $deepLink")
            //val providerId = it.pathSegments[it.pathSegments.size-1]
            Log.d("NJW", "providerId: $deepLink")
            loadFragment(deepLink.getQueryParameter("module"))

        }

    }

    private fun loadFragment(module: String) {
        val arguments = Bundle()
        arguments.putString(ModuleDetailFragment.ARG_ITEM_ID, module)
        val fragment = ModuleDetailFragment()
        fragment.arguments = arguments
        supportFragmentManager.beginTransaction()
                .replace(R.id.module_detail_container, fragment)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    navigateUpTo(Intent(this, ModuleListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}

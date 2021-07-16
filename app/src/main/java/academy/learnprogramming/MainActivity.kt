package academy.learnprogramming

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseIam: FirebaseInAppMessaging
    //private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        firebaseIam = FirebaseInAppMessaging.getInstance()

        firebaseIam.isAutomaticDataCollectionEnabled = true
        firebaseIam.setMessagesSuppressed(false)

        eventTriggerButton.setOnClickListener { view ->
            //firebaseAnalytics.logEvent("engagement_party", Bundle())
            firebaseIam.triggerEvent("engagement_party")
        }

        // Get and display/log the Instance ID
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener(object : OnSuccessListener<InstanceIdResult> {
                override fun onSuccess(instanceIdResult: InstanceIdResult) {
                    val instanceId = instanceIdResult.id
                    instanceIdText.text = getString(R.string.instance_id_fmt, instanceId)
                    Log.d(TAG, "InstanceId: $instanceId")
                }
            })

        addClickListener()
    }

    private fun addClickListener() {
        val listener = MyClickListener()
        firebaseIam.addClickListener(listener)
    }

    private fun suppressMessages() {
        firebaseIam.setMessagesSuppressed(true)
    }

    private fun enableDataCollection() {
        // Only needed if firebase_inapp_messaging_auto_data_collection_enabled is set to
        // false in AndroidManifest.xml
        firebaseIam.isAutomaticDataCollectionEnabled = true
    }

    companion object {

        private const val TAG = "MyAPP"
    }
}

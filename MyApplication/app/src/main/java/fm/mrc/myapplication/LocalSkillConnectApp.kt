package fm.mrc.myapplication

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.analytics.FirebaseAnalytics

class LocalSkillConnectApp : Application() {
    private val TAG = "LocalSkillConnectApp"

    companion object {
        private var instance: LocalSkillConnectApp? = null
        private var firebaseInitialized = false

        fun getInstance(): LocalSkillConnectApp {
            return instance ?: throw IllegalStateException("Application not initialized")
        }

        fun isFirebaseInitialized(): Boolean = firebaseInitialized
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeFirebase()
    }

    private fun initializeFirebase() {
        try {
            Log.d(TAG, "Starting Firebase initialization")
            
            if (FirebaseApp.getApps(this).isEmpty()) {
                Log.d(TAG, "No Firebase app found, initializing default app")
                
                // Initialize Firebase
                FirebaseApp.initializeApp(applicationContext)?.let { app ->
                    Log.d(TAG, "Firebase app initialized: ${app.name}")
                    
                    // Initialize Firebase components
                    FirebaseAnalytics.getInstance(this)
                    val auth = FirebaseAuth.getInstance()
                    val firestore = FirebaseFirestore.getInstance()
                    
                    Log.d(TAG, "Firebase Auth initialized: ${auth.app.name}")
                    Log.d(TAG, "Firebase Firestore initialized: ${firestore.app.name}")
                    
                    firebaseInitialized = true
                } ?: run {
                    Log.e(TAG, "Failed to initialize Firebase app")
                    firebaseInitialized = false
                }
            } else {
                Log.d(TAG, "Firebase already initialized")
                val app = FirebaseApp.getInstance()
                Log.d(TAG, "Using existing Firebase app: ${app.name}")
                firebaseInitialized = true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize Firebase: ${e.message}", e)
            e.printStackTrace()
            firebaseInitialized = false
        }
    }

    fun reinitializeFirebaseIfNeeded() {
        if (!firebaseInitialized) {
            Log.d(TAG, "Attempting to reinitialize Firebase")
            initializeFirebase()
        }
    }
} 
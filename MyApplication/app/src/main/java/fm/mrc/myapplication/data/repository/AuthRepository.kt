package fm.mrc.myapplication.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import fm.mrc.myapplication.LocalSkillConnectApp
import fm.mrc.myapplication.data.models.User
import fm.mrc.myapplication.data.models.UserType
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepository {
    private val TAG = "AuthRepository"
    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null

    init {
        try {
            Log.d(TAG, "Initializing AuthRepository")
            initializeFirebase()
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing AuthRepository: ${e.message}", e)
        }
    }

    private fun initializeFirebase() {
        try {
            if (!LocalSkillConnectApp.isFirebaseInitialized()) {
                Log.w(TAG, "Firebase not initialized at repository level")
                LocalSkillConnectApp.getInstance().reinitializeFirebaseIfNeeded()
            }
            
            auth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            
            Log.d(TAG, "Firebase Auth instance: ${auth?.app?.name}")
            Log.d(TAG, "Firebase Firestore instance: ${firestore?.app?.name}")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing Firebase services: ${e.message}", e)
            throw e
        }
    }

    private fun ensureInitialized() {
        if (auth == null || firestore == null) {
            Log.w(TAG, "Firebase services not initialized, attempting to initialize")
            initializeFirebase()
        }
    }

    suspend fun signUp(
        email: String,
        password: String,
        name: String,
        phoneNumber: String,
        userType: UserType
    ): Result<User> = try {
        ensureInitialized()
        Log.d(TAG, "Attempting to sign up user: $email")
        
        val authResult = auth?.createUserWithEmailAndPassword(email, password)?.await()
            ?: throw Exception("Firebase Auth not initialized")
            
        val user = authResult.user ?: throw Exception("Failed to create user")
        Log.d(TAG, "User created successfully: ${user.uid}")

        val newUser = User(
            uid = user.uid,
            email = email,
            name = name,
            phoneNumber = phoneNumber,
            userType = userType
        )

        Log.d(TAG, "Saving user data to Firestore")
        firestore?.collection("users")
            ?.document(user.uid)
            ?.set(newUser)
            ?.await()
            ?: throw Exception("Firestore not initialized")

        Log.d(TAG, "User data saved successfully")
        Result.success(newUser)
    } catch (e: Exception) {
        Log.e(TAG, "Error in signUp: ${e.message}", e)
        Result.failure(e)
    }

    suspend fun signIn(email: String, password: String): Result<User> = try {
        ensureInitialized()
        Log.d(TAG, "Attempting to sign in user: $email")
        
        val authResult = auth?.signInWithEmailAndPassword(email, password)?.await()
            ?: throw Exception("Failed to sign in")
            
        val firebaseUser = authResult.user ?: throw Exception("Failed to sign in")
        Log.d(TAG, "User signed in successfully: ${firebaseUser.uid}")

        Log.d(TAG, "Fetching user data from Firestore")
        val userDoc = firestore?.collection("users")
            ?.document(firebaseUser.uid)
            ?.get()
            ?.await()
            ?: throw Exception("Failed to fetch user data")

        val user = userDoc.toObject(User::class.java) ?: throw Exception("User data not found")
        Log.d(TAG, "User data fetched successfully")
        Result.success(user)
    } catch (e: Exception) {
        Log.e(TAG, "Error in signIn: ${e.message}", e)
        Result.failure(e)
    }

    suspend fun getUserData(uid: String): User = try {
        ensureInitialized()
        val userDoc = firestore?.collection("users")
            ?.document(uid)
            ?.get()
            ?.await()
            ?: throw Exception("Failed to fetch user data")

        userDoc.toObject(User::class.java) ?: throw Exception("User data not found")
    } catch (e: Exception) {
        Log.e(TAG, "Error in getUserData: ${e.message}", e)
        throw e
    }

    suspend fun signOut(): Result<Unit> = try {
        ensureInitialized()
        auth?.signOut()
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e(TAG, "Error in signOut: ${e.message}", e)
        Result.failure(e)
    }

    fun getCurrentUser(): FirebaseUser? {
        ensureInitialized()
        return auth?.currentUser
    }

    suspend fun resetPassword(email: String): Result<Unit> = try {
        ensureInitialized()
        auth?.sendPasswordResetEmail(email)?.await()
            ?: throw Exception("Failed to send password reset email")
        Result.success(Unit)
    } catch (e: Exception) {
        Log.e(TAG, "Error in resetPassword: ${e.message}", e)
        Result.failure(e)
    }
} 
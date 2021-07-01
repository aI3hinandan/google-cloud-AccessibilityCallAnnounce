package com.gearlabs.accessibilitycallannounce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
const val requestIdToken = "545466549369-ebaue5uni0ttlqfnn3tbf1s2pl0qed0d.apps.googleusercontent.com"
class AuthenticationActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private val TAG = "ClassName"
    }

    private lateinit var auth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    //lateinit var callbackManager: CallbackManager
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
         val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(requestIdToken)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        findViewById<Button>(R.id.button_google).setOnClickListener(this)
        auth = FirebaseAuth.getInstance()

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id == R.id.button_google) {
                singIn()
            }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun singIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task: com.google.android.gms.tasks.Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
       // callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   private  fun handleGoogleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun firebaseAuthWithGoogle  (account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id!!)

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    FirestoreDatabase().initialCalls()
                    updateUI(user)
                }  else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun updateUI(user: FirebaseUser?) {
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
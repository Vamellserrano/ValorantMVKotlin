package com.example.valorantpruebaapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.valorantpruebaapi.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


enum class ProviderType {
    BASIC
}
class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Acceso al menú principal CON LOGIN
        binding.buttonSignin.setOnClickListener {
            //Comprobamos que los campos no estén vacios
            if (binding.tilUser.text!!.isNotEmpty() && binding.tilPw.text!!.isNotEmpty()) {
                //Creamos una instancia de Firebase con una función de crear usuario con el correo y la contraseña
                //Como parámetros le pasamos nuestros campos de texto de los cuales cogerá la información
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.tilUser.text.toString(),
                    binding.tilPw.text.toString()
                ).addOnCompleteListener {
                    //Si no hay fallos, registrará el usuario y nos devolverá a la pantalla de login
                    if (it.isSuccessful) {
                        showMain(it.result.user?.email, ProviderType.BASIC)
                        //En caso que no se pueda realizar el registro, dará una pantalla de error
                    } else {
                        showAlertLoginError()
                    }
                }
            } else {
                showAlertLoginError()
            }
        }

        //Acceso al menú principal SIN LOGIN
        binding.buttonContinue.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainScreen::class.java)
            startActivity(intent)
        }

        //Acceso a la pantalla de registro
        binding.buttonSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterScreen::class.java)
            startActivity(intent)
        }

        //Acceso al menú principal a través de Google
        binding.buttonSigningoogle.setOnClickListener {
            checkCurrentUser()
        }

        // Configuración del Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize Firebase Auth
        auth = Firebase.auth
    }
    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        val guser = GoogleSignIn.getLastSignedInAccount(applicationContext)
        if (user != null || guser != null) {
            googleSignInClient.signOut()
            signIn()
        } else {
            signIn()
        }
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
                val intent = Intent(this@LoginActivity, MainScreen::class.java)
                startActivity(intent)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    private fun showAlertLoginError(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("ERROR")
        builder.setMessage("Se ha producido un error al iniciar sesión. Por favor, revise los datos introducidos")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showMain(email: String?, provider: ProviderType) {
        val mainIntent = Intent(this@LoginActivity, MainScreen::class.java).apply {
            putExtra("EMAIL", email)
            putExtra("PROVIDER", provider.name)
        }
        startActivity(mainIntent)
    }
}
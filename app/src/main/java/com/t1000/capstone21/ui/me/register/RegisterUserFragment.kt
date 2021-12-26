package com.t1000.capstone21.ui.me.register

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.t1000.capstone21.databinding.FragmentRegisterUserBinding
import com.t1000.capstone21.models.User
import java.lang.Exception


private const val TAG = "RegisterUserFragment"
class RegisterUserFragment : Fragment(){

    private lateinit var binding: FragmentRegisterUserBinding
    private lateinit var auth: FirebaseAuth
    private val userFirestore = Firebase.firestore
    private var selectedPhotoUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterUserBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerLoginBtn.setOnClickListener{
            registerUser()
        }

        binding.profilePicBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }


    private fun registerUser(){


        val email = binding.registerEmail.text.toString()
        val password = binding.registerPassword.text.toString()
        val username = binding.registerUserName.text.toString()


        if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
            try {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = User(userId = auth?.uid!!, username = username)
                            userFirestore.collection("users")
                                .document(Firebase.auth?.uid!!)
                                .set(currentUser)
                            Toast.makeText(context, "successfully register user $username", Toast.LENGTH_LONG).show()
                        }
                        Toast.makeText(context, "fi $username", Toast.LENGTH_LONG).show()

                    }
            }catch (e:Exception){
                Toast.makeText(context,"filed register$e",Toast.LENGTH_LONG).show()

            }

            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhotoUri = data.data ?: return
            Log.d(TAG, "Photo was selected")
            // Get and resize profile image
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//            contentResolver.query(selectedPhotoUri!!, filePathColumn, null, null, null)?.use {
//                it.moveToFirst()
//                val columnIndex = it.getColumnIndex(filePathColumn[0])
//                val picturePath = it.getString(columnIndex)
//                if (picturePath.contains("DCIM")) {
//                    binding.profilePicView.load(selectedPhotoUri)
//                } else {
//                    binding.profilePicView.load(selectedPhotoUri)
//                }
//
//            }
        }
    }

    }




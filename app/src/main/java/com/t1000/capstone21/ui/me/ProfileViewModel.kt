package com.t1000.capstone21.ui.me

import androidx.lifecycle.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.t1000.capstone21.Repo
import com.t1000.capstone21.models.User
import com.t1000.capstone21.models.Video
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val repo = Repo.getInstance()



    fun fetchUser(): LiveData<User?> = liveData {

        repo.fetchUser()


    }


}
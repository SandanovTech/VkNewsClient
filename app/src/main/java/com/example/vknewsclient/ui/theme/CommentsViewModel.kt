package com.example.vknewsclient.ui.theme

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.PostComment

class CommentsViewModel : ViewModel() {
    private val _screenState =  MutableLiveData<CommentsScreenState>(CommentsScreenState.InitialState)
    val screenState : LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(FeedPost())
    }
    fun loadComments(feedPost: FeedPost) {
        val comments = mutableListOf<PostComment>().apply {
            repeat(10){
                add(PostComment(id = it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(
            posts = feedPost, comments = comments
        )
    }
}
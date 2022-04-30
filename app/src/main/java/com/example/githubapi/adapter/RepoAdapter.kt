package com.example.githubapi.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapi.R
import com.example.githubapi.rest.response.GitRepoData

abstract class RepoAdapter(
    private val context : Context,
    private val list: List<GitRepoData>
) : RecyclerView.Adapter<RepoAdapter.RepoVH>() {

    abstract fun moveToDetailScreen(gitRepoData: GitRepoData)

    class RepoVH(view : View) : RecyclerView.ViewHolder(view){
        val repoImageIv : ImageView = view.findViewById(R.id.repo_image_iv)
        val repoNameTv : TextView = view.findViewById(R.id.repo_name_tv)
        val layoutCl : ConstraintLayout = view.findViewById(R.id.layout_cl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending_repo,parent,false)
        return RepoVH(view)
    }

    override fun onBindViewHolder(holder: RepoVH, position: Int) {
        val currentRepo = list[position]

        holder.repoNameTv.text = currentRepo.fullName
        currentRepo.owner?.let {
            if (!TextUtils.isEmpty(it.avatarUrl)){
                Glide.with(context).load(it.avatarUrl).into(holder.repoImageIv)
            }
        }

        holder.layoutCl.setOnClickListener {
            moveToDetailScreen(currentRepo)
        }

    }

    override fun getItemCount(): Int = list.size

}
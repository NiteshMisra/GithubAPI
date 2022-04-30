package com.example.githubapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.MyAppViewModel
import com.example.githubapi.R
import com.example.githubapi.adapter.RepoAdapter
import com.example.githubapi.databinding.FragmentRepoListBinding
import com.example.githubapi.rest.response.GitRepoData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepoListFragment : Fragment() {

    private lateinit var binding : FragmentRepoListBinding

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[MyAppViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_repo_list, container, false)
        binding.repoRcv.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getALLRepo()

        lifecycleScope.launch {
            viewModel.allRepo.collect{
                it?.let {
                    val adapter = object : RepoAdapter(requireContext(),it.repos){
                        override fun moveToDetailScreen(gitRepoData: GitRepoData) {
                            findNavController().navigate(RepoListFragmentDirections.showRepoDetail(gitRepoData))
                        }

                    }
                    binding.repoRcv.adapter = adapter
                }
            }
        }

        lifecycleScope.launch {
            viewModel.progressLoading.collect{ isLoading ->
                if (isLoading) binding.progressBar.visibility = View.VISIBLE
                else binding.progressBar.visibility = View.GONE
            }
        }

    }

}
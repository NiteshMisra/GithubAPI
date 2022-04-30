package com.example.githubapi.fragments

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.githubapi.R
import com.example.githubapi.databinding.FragmentRepoDetailBinding
import com.example.githubapi.rest.response.GitRepoData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailFragment : Fragment() {

    private var repoData: GitRepoData? = null
    private lateinit var binding : FragmentRepoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val args = RepoDetailFragmentArgs.fromBundle(it)
            repoData = args.gitRepoData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repo_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repoData?.let {

            val url = "https://raw.githubusercontent.com/${it.fullName}/${it.defaultBranch}/README.md"

            binding.webView.settings.javaScriptEnabled = true

            binding.webView.setWebViewClient(object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView,
                    errorCode: Int,
                    description: String,
                    failingUrl: String
                ) {
                    Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
                }

                @TargetApi(Build.VERSION_CODES.M)
                override fun onReceivedError(
                    view: WebView,
                    req: WebResourceRequest,
                    rerr: WebResourceError
                ) {
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    onReceivedError(
                        view,
                        rerr.errorCode,
                        rerr.description.toString(),
                        req.url.toString()
                    )
                }
            })

            binding.webView.loadUrl(url)
        }


    }

}
    package com.dicoding.asclepius.adapters

    import android.annotation.SuppressLint
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.DiffUtil
    import androidx.recyclerview.widget.ListAdapter
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.dicoding.asclepius.data.remote.response.ArticlesItem
    import com.dicoding.asclepius.databinding.ArticleLayoutBinding

    class ArticlesAdapter() : ListAdapter<ArticlesItem, ArticlesAdapter.ViewHolder>(DIFF_CALLBACK) {
        inner class ViewHolder(private val binding: ArticleLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(article: ArticlesItem) {
                // check if the image is null, if it is then display a default image
                if (article.urlToImage == null) {
                    Glide.with(binding.root.context)
                        // I host the default image in discord
                        .load("https://cdn.discordapp.com/attachments/1231980333399015477/1233585134788153354/pexels-ella-olsson-572949-1640770.jpg?ex=662da12f&is=662c4faf&hm=1bd0e6fbb931ed4cdd1fd01cc5631fbb8142ee8f4b4a8add870b0b7b9c7ca7f9&")
                        .into(binding.ivArticles)
                } else {
                    Glide.with(binding.root.context)
                        .load(article.urlToImage)
                        .into(binding.ivArticles)
                }
                binding.tvTitle.text = article.title
                binding.tvDescription.text = article.description.toString()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesAdapter.ViewHolder {
            val binding = ArticleLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ArticlesAdapter.ViewHolder, position: Int) {
            val article = getItem(position)
            if (article.title != "[Removed]") {
                holder.itemView.visibility = View.VISIBLE
                holder.itemView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                holder.bind(article)
            } else {
                holder.itemView.visibility = View.GONE
                holder.itemView.layoutParams.height = 0
            }
        }

        companion object {
            val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
                override fun areItemsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: ArticlesItem,
                    newItem: ArticlesItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
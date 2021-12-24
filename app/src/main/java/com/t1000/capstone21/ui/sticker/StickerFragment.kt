package com.t1000.capstone21.ui.sticker

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.t1000.capstone21.databinding.ItemStickerBinding
import com.t1000.capstone21.databinding.StickerFragmentBinding
import com.t1000.capstone21.stipop.models.Sticker

private const val TAG = "StickerFragment"
class StickerFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(StickerViewModel::class.java) }


    private lateinit var binding :StickerFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StickerFragmentBinding.inflate(layoutInflater)

        binding.stickerRv.layoutManager = GridLayoutManager(context,3)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dataLiveData.observe(
            viewLifecycleOwner,
            Observer {
                binding.stickerRv.adapter = StickersAdapter(it)
            }
        )
    }


    private inner class StickersHolder(val binding: ItemStickerBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(sticker: Sticker){

          // binding.StickerTv.load("https://en.wikipedia.org/wiki/Image#/media/File:Image_created_with_a_mobile_phone.png")

            binding.StickerTv.text = sticker.url

            Log.d(TAG, "bind: sssssssssssssssssss")

        }
    }


    private inner class StickersAdapter(val stickers:List<Sticker>):
        RecyclerView.Adapter<StickersHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickersHolder {
            val binding = ItemStickerBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return StickersHolder(binding)

        }

        override fun onBindViewHolder(holder: StickersHolder, position: Int) {
            val sticker = stickers[position]
            holder.bind(sticker)
        }

        override fun getItemCount(): Int  = stickers.size
    }



}
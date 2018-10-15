package me.paixao.atmlist.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import me.paixao.atmlist.R
import me.paixao.atmlist.data.Atm
import me.paixao.atmlist.utils.addAllIfNotIn


class AtmListAdapter(val atms: MutableList<Atm>) : RecyclerView.Adapter<AtmListAdapter.AtmViewHolder>() {

    private val clickSubject = PublishSubject.create<Atm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.recyclerview_atm_list_item, parent, false)

        return AtmViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AtmViewHolder, position: Int) {
        holder.bind(atms[position])
    }

    override fun getItemCount() = atms.size

    fun getViewClickedObservable(): Observable<Atm> {
        val clickEvent: Observable<Atm> = clickSubject
        return clickEvent
    }

    fun reset(newAtms: List<Atm>?) {
        if (newAtms != null) {
            this.atms.clear()
            this.atms.addAll(newAtms)
            notifyDataSetChanged()
        }
    }

    fun addAtms(newAtms: List<Atm>?) {
        if (newAtms != null) {
            this.atms.addAllIfNotIn(newAtms)
            notifyDataSetChanged()
        }
    }

    inner class AtmViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                clickSubject.onNext(atms[layoutPosition])
            }
        }

        fun bind(atm: Atm) {
            binding.setVariable(BR.viewmodel, AtmListItemViewModel(binding.root.context, atm))
            binding.executePendingBindings()
        }
    }
}



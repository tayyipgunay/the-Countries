package com.tayyipgunay.thecountries2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tayyipgunay.thecountries2.model.Country
import com.tayyipgunay.thecountries2.R
import com.tayyipgunay.thecountries2.Util.DownloadUrl
import com.tayyipgunay.thecountries2.Util.placeHolderProgressBar
import com.tayyipgunay.thecountries2.View.CountryFragmentDirections
import com.tayyipgunay.thecountries2.databinding.ListRowBinding
import okhttp3.internal.http2.Http2Connection.Listener

class CountryAdapter(val countryList:ArrayList<Country>) :RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(),CountryClickListener
{


    class CountryViewHolder(var binding:ListRowBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {


      /* val binding=ListRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)*/

         val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListRowBinding>(inflater,R.layout.list_row,parent,false)
        return CountryViewHolder(binding)


    }

    override fun getItemCount(): Int {
    return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
/*holder.binding.countrynameid.text=  countryList.get(position).countryName  //countryList[position].countryName
   holder.binding.countryregionid.text=countryList.get(position).countryRegion*/

        holder.binding.country = countryList[position]
   holder.binding.listener=this//listener implement ettik



/*holder.binding.imageviewid.DownloadUrl(countryList[position].flag, placeHolderProgressBar(holder.itemView.context))


       holder.itemView.setOnClickListener {
            val uuid=countryList.get(position).uuid

            val action= CountryFragmentDirections.actionCountryFragment2ToCounrtyDetailsFragment3(uuid)
            Navigation.findNavController(it).navigate(action)*/

}





    fun UpdateCountryList(newCountryList:List<Country>){
countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

   override fun onCountryClick(v: View) {

        val uuidTextView = v.findViewById<TextView>(R.id.countryUuid)
        val uuid = uuidTextView.text.toString().toInt()
       println("UUID in onCountryClick: $uuid") // UUID'nin doğru göründüğünü kontrol et

        val action= CountryFragmentDirections.actionCountryFragment2ToCounrtyDetailsFragment3(uuid)
        Navigation.findNavController(v).navigate(action)

    }


}
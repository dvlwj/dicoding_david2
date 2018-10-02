package dicoding.david.footballapps.adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import dicoding.david.footballapps.R
import java.text.SimpleDateFormat
import java.util.*
import dicoding.david.footballapps.R.layout.match_list


class NextMatchAdapter(private val dataList: ArrayList<NextMatchModel>?, private val listener: MyListener) : RecyclerView.Adapter<NextMatchAdapter.NextMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(match_list, parent, false)
        return NextMatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        dataList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    inner class NextMatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.customer_name
        private val customerCompany: TextView = itemView.customer_company
        val customerPhone: TextView = itemView.customer_phone
        val customerWA: TextView = itemView.customer_whatsapp
        val customerRegion: TextView = itemView.customer_region
        val customerAddress: TextView = itemView.customer_address
        private val customerStatus: TextView = itemView.date_request
        private val customerDescription: TextView = itemView.customer_description
        private val trainerAccept: Button = itemView.trainer_accept_request
        private val appointmentDateRequest: TextView = itemView.appointment_date_request
        private val appointmentDate: TextView = itemView.appointment_date
        fun bind(model: ModelRequestList){
            val greet = when (model.JenisKelamin) {
                "0" -> itemView.context.resources.getString(R.string.greeting_gender_female, model.NamaCustomer?.capitalize())
                else -> itemView.context.resources.getString(R.string.greeting_gender_male, model.NamaCustomer?.capitalize())
            }
            val indonesiaDateFormat = Locale("in","ID","ID")
            val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(model.InputDateTime)
            val dateTime2 = SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", indonesiaDateFormat).format(dateTime)
            val appointDateRequest = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(model.TglRequest)
            val appointDateRequest2 = SimpleDateFormat("EEEE, dd MMMM yyyy", indonesiaDateFormat).format(appointDateRequest)
            customerName.text = greet
            customerCompany.text = model.NamaPerusahaan
            customerPhone.text = model.NoHp
            when {
                model.Whatsapp.isNullOrEmpty() -> customerWA.text = "-"
                else -> customerWA.text = model.Whatsapp
            }
            customerRegion.text = model.Kecamatan
            customerAddress.text = model.Alamat
            customerStatus.text = itemView.context.resources.getString(R.string.customer_entry_date,dateTime2.toString())
            appointmentDateRequest.text = appointDateRequest2.toString()
            when (model.TglAppointment) {
                null -> appointmentDate.text = itemView.context.getString(R.string.if_appointment_date_blank)
                else -> {
                    val appointDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(model.TglAppointment)
                    val appointDate2 = SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", indonesiaDateFormat).format(appointDate)
                    appointmentDate.text = appointDate2.toString()
                }
            }
            customerDescription.text = model.Deskripsi
            trainerAccept.visibility = if (model.Status == "1") View.VISIBLE else View.GONE
            customerPhone.setOnClickListener{
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:"+model.NoHp)
                itemView.context.startActivity(callIntent)
            }
            customerWA.setOnClickListener {
                val nomor = model.Whatsapp?.replace(" ", "")?.replace("+", "")
                try{
                    val uri = Uri.parse("smsto:$nomor")
                    val i = Intent(Intent.ACTION_SENDTO, uri)
                    i.putExtra("sms_body","")
                    i.setPackage("com.whatsapp")
                    itemView.context.startActivity(i)
                } catch(e: Exception) {
                    toastInfo(itemView.context.resources.getString(R.string.if_whatsapp_not_installed),itemView.context)
                }
            }
            customerAddress.setOnClickListener {
                val mapsIntent = Intent(Intent.ACTION_VIEW)
                val custAddress = customerAddress.text?.toString()
                val mapsUri = "http://maps.google.com/maps?q=$custAddress"
                mapsIntent.data = Uri.parse(mapsUri)
                itemView.context.startActivity(mapsIntent)
            }
            trainerAccept.setOnClickListener {
                listener.onHolderClick(model.RequestID)
            }
        }
    }
    interface MyListener {
        fun onHolderClick(reqID: String?)
    }

}
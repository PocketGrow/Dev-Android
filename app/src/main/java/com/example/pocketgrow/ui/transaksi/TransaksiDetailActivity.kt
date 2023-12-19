package com.example.pocketgrow.ui.transaksi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.pocketgrow.MainActivity
import com.example.pocketgrow.R
import com.example.pocketgrow.ViewModelFactory
import com.example.pocketgrow.api.ApiConfig
import com.example.pocketgrow.api.response.DeleteTransactionResponse
import com.example.pocketgrow.api.response.TransactionDetail
import com.example.pocketgrow.databinding.ActivityTransaksiDetailBinding
import com.example.pocketgrow.helper.AuthPreference
import com.example.pocketgrow.helper.dateFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.Locale

class TransaksiDetailActivity : AppCompatActivity() {
    private val transaksiDetailViewModel by viewModels<TransaksiDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var binding: ActivityTransaksiDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransaksiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sembunyikan Toolbar
        supportActionBar?.hide()

        val storyId = intent.getStringExtra("transaction_id")

        storyId?.let { transaksiDetailViewModel.getDetailTr(it) }

        transaksiDetailViewModel.jikaBerhasil.observe(this) {
            getTransaksiDetail(it)
        }

        binding.btnDelete.setOnClickListener {
            // Mendapatkan ID transaksi dari intent
            val transactionId = intent.getStringExtra("transaction_id")

            // Menampilkan dialog konfirmasi sebelum menghapus
            transactionId?.let {
                showDeleteConfirmationDialog(it)
            }
        }

    }

    private fun getTransaksiDetail(data: TransactionDetail) {
        binding.namaThumbnail.text = data.name
        binding.categoryThumbnail.text = data.type
        val formattedNominal = NumberFormat.getNumberInstance(Locale("id", "ID")).format(data.nominal)
        binding.hargaThumbnail.text = "Rp. $formattedNominal"
        binding.tanggalThumbnail.text = dateFormat(data.date)

        // Set warna teks nominal berdasarkan tipe kategori
        val colorResId = if (data.type == "INCOME") {
            R.color.green // Warna hijau untuk income
        } else {
            R.color.red // Warna merah untuk expense
        }

        binding.hargaThumbnail.setTextColor(ContextCompat.getColor(this, colorResId))
    }

    private fun deleteTransaction(transactionId: String) {
        // Mengambil token otorisasi dari SharedPreference atau tempat penyimpanan lainnya
        val authPreference = AuthPreference(applicationContext)
        val key = authPreference.getValue("key")

        // Mendapatkan instance dari ApiService
        val apiService = ApiConfig().getApiService()

        // Melakukan pemanggilan fungsi deleteTransaction
        val deleteCall = apiService.deleteTransaction("Bearer $key", transactionId)
        deleteCall.enqueue(object : Callback<DeleteTransactionResponse> {
            override fun onResponse(call: Call<DeleteTransactionResponse>, response: Response<DeleteTransactionResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Transaksi berhasil dihapus",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Pindah ke MainActivity setelah berhasil menghapus transaksi
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Menutup activity setelah berhasil menghapus transaksi

                } else {
                    // Gagal menghapus transaksi, tangani kesalahan di sini
                    Toast.makeText(
                        applicationContext,
                        "Gagal menghapus transaksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<DeleteTransactionResponse>, t: Throwable) {
                // Tangani kegagalan komunikasi
                Toast.makeText(
                    applicationContext,
                    "Gagal menghapus transaksi: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }
    private fun showDeleteConfirmationDialog(transactionId: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to delete this transaction?")

        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            // Jika pengguna menekan tombol "Ya", maka hapus transaksi
            deleteTransaction(transactionId)
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            // Jika pengguna menekan tombol "Tidak", tutup dialog tanpa menghapus
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}
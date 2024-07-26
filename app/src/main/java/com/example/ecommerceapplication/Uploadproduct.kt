package com.example.ecommerceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class Uploadproduct : AppCompatActivity() {
    private lateinit var pimg : ImageView
    private lateinit var pname : EditText
    private lateinit var pprice : EditText
    private lateinit var pdes : EditText
    private lateinit var spro : Button
    private lateinit var upro : Button
    private lateinit var pb : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploadproduct)

        pimg = findViewById(R.id.pro_img)
        pname = findViewById(R.id.pro_name)
        pprice = findViewById(R.id.pro_price)
        pdes = findViewById(R.id.pro_des)
        spro = findViewById(R.id.select_product_image)
        upro = findViewById(R.id.upload_product_image)
        pb = findViewById(R.id.progress_bar)

        spro.setOnClickListener {
            startActivityForResult(
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                101
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == RESULT_OK){
            val uri = data?.data
            pimg.setImageURI(uri)

            upro.setOnClickListener {
                pb.visibility = View.VISIBLE
                val productname = pname.text.toString()
                val productprice = pprice.text.toString()
                val productdesc = pdes.text.toString()

                val filename = UUID.randomUUID().toString()+".jpg"
                val storageRef = FirebaseStorage.getInstance().reference.child("productImages/$filename")
                storageRef.putFile(uri!!).addOnSuccessListener {
                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener {
                        upload(
                            productname,
                            productprice,
                            productdesc,
                            it.toString()
                        )
                    }
                }
            }
        }
    }

    private fun upload(name : String, price : String, desc : String, link : String){
        Firebase.database.getReference("Products").child(name).setValue(
            product(
                productName = name,
                productPrice = price,
                productDes = desc,
                productImage = link
            )
        ).addOnSuccessListener {
            pb.visibility = View.GONE
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            Toast.makeText(this, "Product Added Sucessfully", Toast.LENGTH_SHORT).show()
        }
    }
}
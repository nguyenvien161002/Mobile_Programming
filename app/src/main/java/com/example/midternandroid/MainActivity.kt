package com.example.midternandroid

import android.R.attr
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midternandroid.database.AppDatabase
import com.example.midternandroid.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt


class MainActivity : AppCompatActivity() {

    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDatabase = AppDatabase.getDatabase(this)

        btnSignUp.setOnClickListener {
            insertUser()
        }

    }


    private fun insertUser() {
        val txtUserName = userName.text.toString()
        val txtUserEmail = userEmail.text.toString()
        val txtPassword = password.text.toString()
        val txtConfirmPassword = confirmPassword.text.toString()
        val validEmail = isEmailValid(txtUserEmail)

        if (txtUserName.isNotEmpty() && txtUserEmail.isNotEmpty() &&
            txtPassword.isNotEmpty() && txtConfirmPassword.isNotEmpty()) {

            // Kiểm tra xem người dùng nhập dúng định dạng email chưa
            if (validEmail) { // Người dùng nhập đúng định dạng email
                if (txtPassword == txtConfirmPassword) { // Xác nhận mật khẩu trùng khớp
                    // Mã hóa password trc khi lưu vào csdl
                    val hashedPassword = BCrypt.hashpw(txtPassword, BCrypt.gensalt())
                    if (BCrypt.checkpw(txtPassword, hashedPassword)) {
                        val user = User(
                            id_user = null,
                            user_email = txtUserEmail,
                            user_name = txtUserName,
                            password = hashedPassword,
                            confirm_password = hashedPassword
                        )
                        GlobalScope.launch(Dispatchers.IO) {
                            appDatabase.userDao().insert(user)
                            userName.setText("")
                            userEmail.setText("")
                            password.setText("")
                            confirmPassword.setText("")
                        }
                        Toast.makeText(this@MainActivity, "Đăng ký tài khoản thành công!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Lỗi rồi! Vui lòng thử lại!", Toast.LENGTH_LONG).show()
                    }
                } else { // Xác nhận mật khẩu không trùng khớp
                    Toast.makeText(this@MainActivity, "Mật khẩu không trùng khớp!", Toast.LENGTH_LONG).show()
                }
            } else { // Người dùng nhập sai định dạng email
                Toast.makeText(this@MainActivity, "Vui lòng nhập đúng định dạng Email!", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this@MainActivity, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}
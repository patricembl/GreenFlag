package com.example.android.greenflag

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.greenflag.databinding.ActivityCreateAccountBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.regex.Pattern

private val LOG_TAG = MainActivity::class.java.simpleName

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding

    private var useralldata: ArrayList<UserData>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(LOG_TAG, "Create Account Activity created")

        binding.backs.setOnClickListener {
            finish()
        }
        binding.editEmail.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            //best solution I could find for this assignment, although getDrawable is deprecated.
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (isValidEmail(binding.editEmail.text.toString().trim())) {
                    binding.editEmail.background = getDrawable(R.drawable.greens_bg)
                    binding.emailok.visibility = View.VISIBLE

                } else if (binding.editEmail.text.toString().trim().equals("")) {
                    binding.editEmail.background = getDrawable(R.drawable.whites_bg)
                    binding.emailok.visibility = View.GONE
                } else {
                    binding.editEmail.background = getDrawable(R.drawable.reds_bg)
                    binding.emailok.visibility = View.GONE

                }
            }
        })

        binding.editPass.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                isValidPassword(binding.editPass.text.toString().trim())
            }
        })

        binding.confirmPass.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

                isValidPassword1(binding.confirmPass.text.toString().trim())
            }
        })

        binding.imageButton.setOnClickListener {

            val sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE)
            val gson = Gson()
            val json = sharedPreferences.getString("users", null)
            val type: Type = object : TypeToken<ArrayList<UserData?>?>() {}.type
            useralldata = gson.fromJson<Any>(json, type) as ArrayList<UserData>?
            if (useralldata == null) {
                useralldata = ArrayList()
            }

            if (binding.editEmail.text.toString().trim().equals("")) {
                binding.emailErr.visibility = View.VISIBLE
                binding.errorEmailText.setText("Please Enter Your Valid Email Address.")
                return@setOnClickListener

            } else {
                binding.emailErr.visibility = View.GONE

            }

            if (!isValidEmail(binding.editEmail.text.toString())) {
                binding.emailErr.visibility = View.VISIBLE
                binding.errorEmailText.setText("Please Enter Your Valid Email Address.")
                return@setOnClickListener

            } else {
                binding.emailErr.visibility = View.GONE
            }

            if (binding.editPass.text.toString().trim().equals("")) {
                binding.passErr.visibility = View.VISIBLE
                binding.errorPassText.setText("Please Enter Valid Password.")
                return@setOnClickListener

            } else {
                binding.passErr.visibility = View.GONE

            }

            if (binding.confirmPass.text.toString().trim().equals("")) {
                binding.passErr.visibility = View.VISIBLE
                binding.errorPassText.setText("Please Enter Valid Password.")
                return@setOnClickListener

            } else {
                binding.passErr.visibility = View.GONE

            }

            if (!isValidPassword(binding.editPass.text.toString().trim())) {
                binding.passErr.visibility = View.VISIBLE
                binding.errorPassText.setText("One of the passwords you entered wasn't valid")
                return@setOnClickListener
            } else {
                binding.passErr.visibility = View.GONE

            }

            if (!isValidPassword1(binding.confirmPass.text.toString().trim())) {
                binding.passErr.visibility = View.VISIBLE
                binding.errorPassText.setText("One of the passwords you entered wasn't valid")
                return@setOnClickListener
            } else {
                binding.passErr.visibility = View.GONE

            }


            if (!binding.editPass.text.toString().trim()
                    .equals(binding.confirmPass.text.toString().trim())
            ) {
                binding.passErr.visibility = View.VISIBLE
                binding.errorPassText.setText("Your passwords don't match")
                return@setOnClickListener
            } else {
                binding.passErr.visibility = View.GONE
            }

            if (useralldata!!.size != 0) {
                for (i in useralldata!!.indices) {
                    val user = useralldata!![i]

                    if (user.u_email.equals(binding.editEmail.text.toString().trim())) {
                        binding.emailErr.visibility = View.VISIBLE
                        binding.errorEmailText.setText("An account already exists for this email address.")
                        return@setOnClickListener
                    }

                    if (i == useralldata!!.size - 1) {
                        useralldata!!.add(
                            UserData(
                                binding.editEmail.text.toString().trim(),
                                binding.confirmPass.text.toString().trim()
                            )
                        )

                        val editor = sharedPreferences.edit()

                        val json1: String = gson.toJson(useralldata)
                        editor.putString("users", json1)
                        editor.apply()

                        binding.editEmail.setText("")
                        binding.editPass.setText("")
                        binding.confirmPass.setText("")

                        Toast.makeText(this, "User Register Successfully.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            else if(useralldata!!.size == 0)
            {
                useralldata!!.add(
                    UserData(
                        binding.editEmail.text.toString().trim(),
                        binding.confirmPass.text.toString().trim()
                    )
                )

//            val sharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
//            val gson = Gson()
                val json1: String = gson.toJson(useralldata)
                editor.putString("users", json1)
                editor.apply()

                binding.editEmail.setText("")
                binding.editPass.setText("")
                binding.confirmPass.setText("")

                Toast.makeText(this, "User Register Successfully.", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun isValidPassword(passtext: String): Boolean {

        // check for pattern
        val uppercase = Pattern.compile("[A-Z]")
        val lowercase = Pattern.compile("[a-z]")
        val digit = Pattern.compile("[0-9]")

        // if lowercase character is not present
        val islowercase = lowercase.matcher(passtext).find()
        // if uppercase character is not present
        val isupercase = uppercase.matcher(passtext).find()
        // if digit is not present
        val isdigit = digit.matcher(passtext).find()
        // if password length is less than 8
        val issizeok = passtext.length >= 8

        if (isdigit && isupercase && islowercase && issizeok) {
            binding.editPass.background = getDrawable(R.drawable.greens_bg)
            binding.passok.visibility = View.VISIBLE

        } else if (binding.editPass.text.toString().trim().equals("")) {
            binding.editPass.background = getDrawable(R.drawable.whites_bg)
            binding.passok.visibility = View.GONE

        } else {
            binding.editPass.background = getDrawable(R.drawable.reds_bg)
            binding.passok.visibility = View.GONE
        }
        return isdigit && isupercase && islowercase && issizeok
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun isValidPassword1(passtext: String): Boolean {

        // check for pattern
        val uppercase = Pattern.compile("[A-Z]")
        val lowercase = Pattern.compile("[a-z]")
        val digit = Pattern.compile("[0-9]")

        // if lowercase character is not present
        val islowercase = lowercase.matcher(passtext).find()
        // if uppercase character is not present
        val isupercase = uppercase.matcher(passtext).find()
        // if digit is not present
        val isdigit = digit.matcher(passtext).find()
        // if password length is less than 8
        val issizeok = passtext.length >= 8

        if (isdigit && isupercase && islowercase && issizeok) {
            binding.confirmPass.background = getDrawable(R.drawable.greens_bg)
            binding.cpassok.visibility = View.VISIBLE

        } else if (binding.confirmPass.text.toString().trim().equals("")) {
            binding.confirmPass.background = getDrawable(R.drawable.whites_bg)
            binding.cpassok.visibility = View.GONE

        } else {
            binding.confirmPass.background = getDrawable(R.drawable.reds_bg)
            binding.cpassok.visibility = View.GONE
        }

        return isdigit && isupercase && islowercase && issizeok
    }
}
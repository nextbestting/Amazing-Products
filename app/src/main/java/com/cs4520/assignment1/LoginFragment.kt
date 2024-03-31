package com.cs4520.assignment1
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class `LoginFragment` : Fragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout
        val view = inflater.inflate(R.layout.login_fragment, container, false)

        // Initializing views
        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //login button click listener
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            //garunteeing username and password are "admin"
            if (username == "admin" && password == "admin") {
                // If yes, navigate to ProductListFragment
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
            } else {
                // error message if passwords dont match
                Toast.makeText(activity, "Wrong username or password please try again.", Toast.LENGTH_SHORT).show()
                usernameEditText.text.clear()
                passwordEditText.text.clear()
            }
        }
    }
}

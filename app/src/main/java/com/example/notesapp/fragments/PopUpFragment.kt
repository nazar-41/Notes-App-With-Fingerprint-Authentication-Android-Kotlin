package com.example.notesapp.fragments

import android.app.Activity.RESULT_OK
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentPopUpBinding
import javax.net.ssl.KeyManager

class PopUpFragment : BaseFragment() {

    private var _binding: FragmentPopUpBinding? = null
    private val binding get() = _binding!!
    private val TAG = "MAIN_TAG"

    private var cancellationSignal: CancellationSignal? = null
    private lateinit var km: KeyguardManager
    private lateinit var fm: FingerprintManager

    private val FINGERPRINT_MANAGER_REQUEST_CODE = 3
    private val KEY_GUARD_REQUEST_CODE = 4

    // create an authenticationCallback
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    if (onPinCodeCreated()) {
                        if (!fm.hasEnrolledFingerprints()) {
                            Toast.makeText(requireContext(), "Register at least 1 fingerprint in settings, and restart the application", Toast.LENGTH_LONG).show()
                            Handler().postDelayed({
                                val intent = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                                startActivity(intent)
                            }, 800)

                        } else {
                            findNavController().navigate(R.id.action_popUpFragment_to_PINCodeFragment)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Please retry again after 30 seconds", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication Succeeded")
                    Log.e(TAG, "onAuthenticationSucceeded: $result")
                    if (onPinCodeCreated()) {
                        findNavController().navigate(R.id.action_popUpFragment_to_mainFragment)
                    } else {
                        findNavController().navigate(R.id.action_popUpFragment_to_authFragment)
                    }
                }
            }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopUpBinding.inflate(inflater, container, false)

        km = requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        fm = requireActivity().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager

        if (!km.isKeyguardSecure) {
            Toast.makeText(
                requireContext(),
                "Lock screen Security is not enabled in settings",
                Toast.LENGTH_SHORT
            ).show()

            Handler().postDelayed({
                val intent = Intent(Settings.ACTION_BIOMETRIC_ENROLL)
                startActivity(intent)
            }, 800)

        }


        checkBiometricSupport()

        val biometricPrompt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BiometricPrompt.Builder(requireContext())
                .setTitle("Title of Prompt")
                .setSubtitle("Subtitle")
                .setNegativeButton("Cancel", requireActivity().mainExecutor, DialogInterface.OnClickListener { _, _ ->
                        notifyUser("Authentication Cancelled")
                    }).build()
        } else {
            TODO("VERSION.SDK_INT < P")
        }

        // start the authenticationCallback in mainExecutor
        biometricPrompt.authenticate(
            getCancellationSignal(),
            requireActivity().mainExecutor,
            authenticationCallback
        )

        return binding.root
    }

    // it will be called when authentication is cancelled by the user
    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was Cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    // it checks whether the app the app has fingerprint permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager =
            requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            notifyUser("Fingerprint authentication has not been enabled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("Fingerprint Authentication Permission is not enabled")
            return false
        }
        return if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true

    }

    private fun notifyUser(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun onPinCodeCreated(): Boolean {
        val sharedPref =
            requireActivity().getSharedPreferences("onPinCodeCreated", Context.MODE_PRIVATE)

        return sharedPref.getBoolean("Created", false)
    }

}
package br.com.controletransporte.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


object MaskCep {

    private val maskCEP = "#####-###"


    fun unmask(s: String): String {
        return s.replace("[^0-9]*".toRegex(), "")
    }

    fun insert(editText: EditText): TextWatcher {
        return object : TextWatcher {
             var isUpdating: Boolean = false
             var old = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                val mask: String
                val defaultMask = getDefaultMask(str)
                when (str.length) {
                    8 -> mask = maskCEP

                    else -> mask = defaultMask
                }

                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length || m != '#' && str.length < old.length && str.length != i) {
                        mascara += m
                        continue
                    }

                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                editText.setText(mascara)
                editText.setSelection(mascara.length)
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }

    private fun getDefaultMask(str: String): String {
        var defaultMask = maskCEP

        return defaultMask
    }
}
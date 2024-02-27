package pe.cibertec.dami.recyclerapplication.util

import android.app.Activity
import android.app.AlertDialog
import pe.cibertec.dami.recyclerapplication.R

class LoadingDialog(val myActivity: Activity) {

    private lateinit var dialog: AlertDialog

    fun startLoading() {
        val inflater = myActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_layout, null)
        dialogView.alpha = 0.99F

        val builder = AlertDialog.Builder(myActivity)
        builder.setView(dialogView)

        builder.setTitle("Cargando datos")

        dialog = builder.create()
        dialog.show()
    }

    fun finishLoading() {
        dialog.dismiss()
    }

}
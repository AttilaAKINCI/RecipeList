package com.akinci.recipelist.commons.components.list

import android.view.View

class RecyclerViewClickListener( val clickListener : (data:Any, view: View) -> Unit) {
    fun onClick(data:Any, view: View) = clickListener(data, view)
}
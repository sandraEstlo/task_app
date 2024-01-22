package com.example.a001_task_app_version_final.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.a001_task_app_version_final.R

open class SwipeToDeleteCallback (private val context: Context, private val taskAdapter: MostrarTareas) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete)
    private val inWidth = deleteIcon?.intrinsicWidth
    private val inHeight = deleteIcon?.intrinsicHeight
    private val background = ColorDrawable()

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        taskAdapter.removeItem(position)
    }


    @SuppressLint("ResourceType")
    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(canvas)

        val iconTop = itemView.top + (itemHeight - inHeight!!) / 2
        val iconMargin = (itemHeight - inHeight) / 2
        val iconLeft = itemView.right - iconMargin - inWidth!!
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + inHeight

        deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        deleteIcon?.draw(canvas)

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
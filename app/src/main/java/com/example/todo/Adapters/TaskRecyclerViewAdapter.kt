package com.example.todo.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.example.todo.Database.Task
import com.example.todo.R

import com.example.todo.Fragments.dummy.DummyContent.DummyItem
import java.text.SimpleDateFormat

class TaskRecyclerViewAdapter internal constructor(
    context: Context
)  : RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tasks = emptyList<Task>()
    var onDoneClick: ((Task) -> Unit)? = null
    var taskClick: ((Task) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.fragment_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val item = tasks[position]
        holder.doneCheckBox.isChecked = item.done
        holder.contentView.text = item.title
        item.due?.let {
            holder.dueView.text  = dateFormat.format(item.due)
        }
        holder.addListener(item)
    }

    override fun getItemCount(): Int = tasks.size

    internal fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doneCheckBox: CheckBox = view.findViewById(R.id.checkBoxDone)
        val contentView: TextView = view.findViewById(R.id.content)
        val dueView: TextView = view.findViewById(R.id.textViewDue)
        val contentLayout: LinearLayout = view.findViewById(R.id.layout_content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        fun addListener(task: Task) {
            doneCheckBox.setOnClickListener {
                if (doneCheckBox.isChecked != task.done) {
                    task.done = doneCheckBox.isChecked
                    onDoneClick?.invoke(task)
                }
            }

            contentLayout.setOnClickListener { taskClick?.invoke(task) }
        }

    }
}

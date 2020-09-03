package com.example.todo.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todo.Adapters.TaskRecyclerViewAdapter
import com.example.todo.R
import com.example.todo.ViewModels.TaskViewModel
import com.example.todo.inTransaction

/**
 * A fragment representing a list of Items.
 */
class TaskFragment : Fragment() {

    private var columnCount = 1
    private var taskViewModel: TaskViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_task_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.list_task)
                val adapter = TaskRecyclerViewAdapter(context)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = layoutManager

                taskViewModel = activity?.let { ViewModelProvider(it).get(TaskViewModel::class.java) }
                taskViewModel?.allTasks?.observe(viewLifecycleOwner, Observer { tasks ->
                    tasks?.let { adapter.setTasks(it) }
                })

                adapter.onDoneClick = { task -> taskViewModel?.update(task) }
                adapter.taskClick = { task ->
                    taskViewModel?.select(task)
                    parentFragmentManager.inTransaction { replace(R.id.content, AddTaskFragment()) }
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
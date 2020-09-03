package com.example.todo.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todo.Database.Task
import com.example.todo.R
import com.example.todo.ViewModels.TaskViewModel
import com.example.todo.inTransaction
import com.example.todo.showAlert
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTaskFragment : Fragment() {
    private lateinit var nameEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var saveButton: Button
    private var taskViewModel: TaskViewModel? = null
    private val dateFormat = SimpleDateFormat("MM/dd/yyyy")
    private var selected: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskViewModel = activity?.let { ViewModelProvider(it).get(TaskViewModel::class.java) }
        view?.let { view ->
            bindViews(view)

            this.dateEditText.setOnClickListener { dateClicked(it) }
            this.saveButton.setOnClickListener { task -> taskViewModel?.let { saveTask(task, it) } }
        }

        viewLifecycleOwner?.let { lifecycleOwner ->
            taskViewModel?.getSelected()?.observe(lifecycleOwner, Observer<Task> { task ->
                task?.let {
                    this.selected = it
                    setUpFields(it)
                }
            })
        }
    }

    private fun bindViews(view: View) {
        this.nameEditText = view.findViewById(R.id.editTextName)
        this.dateEditText = view.findViewById(R.id.editTextDate)
        this.saveButton = view.findViewById(R.id.saveButton)
    }

    private fun setUpFields(task: Task) {
        this.nameEditText.setText(task.title)
        task.due?.let {
            this.dateEditText.setText(dateFormat.format(it))
        }
        this.saveButton.setText(R.string.save)
    }

    private fun dateClicked(view: View) {
        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            this.dateEditText.setText(dateFormat.format(cal.time))
        }

        DatePickerDialog(
            view.context, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun saveTask(view: View, taskViewModel: TaskViewModel) {
        // name is required
        val name = this.nameEditText.text.toString().trim()
        fun getDueDate(): Date? {
            var dueText = this.dateEditText.text.toString().trim()
            if (dueText.isBlank()) {
                return null
            } else {
                var formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
                var localDate = LocalDate.parse(dueText, formatter)
                return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
            }
        }

        fun updateTask(task: Task, taskViewModel: TaskViewModel) {
            task.title = name
            task.due = getDueDate()
            taskViewModel.update(task)
            this.selected = null
            taskViewModel.select(null)
        }

        fun createTask(taskViewModel: TaskViewModel) {
            val task = Task(
                id = 0,
                title = name,
                created = Date(),
                due = getDueDate(),
                description = null,
                priority = 4,
                done = false,
                parentTaskId = null,
                projectId = null,
                sectionId = null,
                index = 0
            )
            taskViewModel.insert(task)
        }

        if (name.isBlank()) {
            showAlert(view, R.string.alert_title_error, getString(R.string.error_task_name_required))
        } else {
            this.selected?.let { updateTask(it, taskViewModel) } ?: createTask(taskViewModel)
            parentFragmentManager?.inTransaction { replace(R.id.content, TaskFragment()) }
        }
    }
}
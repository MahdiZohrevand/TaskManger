package zohrevand.mahdi.taskmanager.view.datepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import zohrevand.mahdi.taskmanager.R
import zohrevand.mahdi.taskmanager.databinding.DatePickerFragmentBinding
import zohrevand.mahdi.taskmanager.utils.setNumberAdapter
import java.util.*

class DatePickerFragment : DialogFragment() {

    private val viewModel: DatePickerViewModel by viewModel()
    private lateinit var binding: DatePickerFragmentBinding
    public var callBack: ((date: Date) -> Unit)? = null

    companion object {
        fun newInstance() = DatePickerFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.date_picker_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.dateDay.setNumberAdapter(1, 31, false)
        binding.dateMonth.setNumberAdapter(1, 12, false)
        binding.dateYear.setNumberAdapter(0, 1420, false)
        binding.datePickerCancel.setOnClickListener {
            dismiss()
        }

        binding.datePickerSubmit.setOnClickListener { _ ->
            viewModel.getDate()?.let { it ->
                callBack?.invoke(
                    it
                )
                dismiss()
            }
        }

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
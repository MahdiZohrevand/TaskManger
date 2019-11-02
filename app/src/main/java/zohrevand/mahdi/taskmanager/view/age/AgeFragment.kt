package zohrevand.mahdi.taskmanager.view.age

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import zohrevand.mahdi.taskmanager.R

class AgeFragment : Fragment() {

    companion object {
        fun newInstance() = AgeFragment()
    }

    private lateinit var viewModel: AgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.age_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AgeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

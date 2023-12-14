package pl.edu.agh.utp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.edu.agh.utp.manager.UserManager
import pl.edu.agh.utp.model.Group
import pl.edu.agh.utp.model.User

class GroupsFragment : Fragment(), GroupAdapter.OnGroupClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var groupAdapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        val addGroupButton: Button = view.findViewById(R.id.add_group_button)
        addGroupButton.setOnClickListener {
            navigateToAddGroupFragment()
        }

        recyclerView = view.findViewById(R.id.groups_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        groupAdapter = GroupAdapter(this)
        recyclerView.adapter = groupAdapter

        fetchUserGroups()

        return view
    }

//    private fun fetchUserGroups(userId: Long) {
//        groupAdapter.setItems(mutableListOf(
//            Group(15, "Nazwa"),
//            Group(1, "To niezla nazwa"),
//            Group(2, "To niezla rozbita swieczka")))
//    }

    private fun fetchUserGroups() {
        if (UserManager(requireContext()).isLoggedIn()) {
            val userId: Long = UserManager(requireContext()).getUser()?.id!!
            groupAdapter.fetchUserGroups(userId)
        }
    }

    private fun navigateToAddGroupFragment() {
        val addGroupFragment = AddGroupFragment(groupAdapter) // TODO: remove passing adapter once dependency injection works
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.groups_recycler_view, addGroupFragment)
        transaction.addToBackStack(null) // TODO: ability to return to previous view
        transaction.commit()
    }

    private fun navigateToTransactionsFragment(groupId: Long) {
        val transactionFragment = TransactionsFragment(groupId)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.groups_recycler_view, transactionFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onGroupClick(groupId: Long) {
        navigateToTransactionsFragment(groupId)
    }
}
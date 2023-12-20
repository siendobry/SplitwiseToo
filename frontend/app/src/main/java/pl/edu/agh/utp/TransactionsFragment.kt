package pl.edu.agh.utp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID


class TransactionsFragment( private val groupId: UUID) : Fragment(),
    TransactionsAdapter.TransactionClickListener {


    override fun onTransactionClick(transactionId: UUID) {
        navigateToTransactionDetailsFragment(transactionId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionsAdapter: TransactionsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val view = inflater.inflate(R.layout.fragment_transactions, container, false)

        val btnAdd: Button = view.findViewById(R.id.add_transaction_button)

        btnAdd.setOnClickListener {
            navigateToAddTransactionFragment()
        }

        recyclerView = view.findViewById(R.id.transactions_recycler_view)
        transactionsAdapter = TransactionsAdapter(groupId, this)
        recyclerView.adapter = transactionsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return view
    }

    override fun onResume() {
        super.onResume()
        transactionsAdapter.fetchTransactions()
    }

    private fun navigateToAddTransactionFragment() {
        val fragment = AddTransactionFragment(groupId)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun navigateToTransactionDetailsFragment(transactionId: UUID) {
        val fragment = TransactionDetailsFragment(transactionId)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }




}
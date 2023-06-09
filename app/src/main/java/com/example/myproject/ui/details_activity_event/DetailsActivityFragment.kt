package com.example.myproject.ui.details_activity_event

import CODE_201
import CODE_204
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myproject.R
import com.example.myproject.databinding.FragmentDetailsActivityBinding
import com.example.myproject.extensions.myToast
import com.example.myproject.extensions.toHydraActivitiesId
import com.example.myproject.sharedviewmodel.SharedViewModel
import com.example.myproject.utils.ConfirmActionDialog
import com.example.myproject.utils.dateFormatter
import com.example.myproject.utils.myPicassoFun
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class DetailsActivityFragment : Fragment() {

    private var _binding: FragmentDetailsActivityBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailsActivityViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val args: DetailsActivityFragmentArgs by navArgs()
    private var bookingIdCurrentUser: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.messageLiveData.observe(this) { message ->
            requireContext().myToast(message)
        }

        // DEPEND IF USER IS AUTHOR, HE CAN ACCESS EDIT, DELETE AND VALIDATION OF
        // PARTICIPANTS

        viewModel.currentUserIdLiveData.observe(this) { currentUserId ->
            if (currentUserId == args.activityEvent.creator.id) {
                binding.btnJoin.visibility = View.GONE
                binding.btnGroupUpdateDelete.visibility = View.VISIBLE
                binding.iconValidation.visibility = View.VISIBLE
            }

            // DEPEND IF USER IS PARTICIPANT, HE CAN ACCESS JOIN AND CANCEL

            args.activityEvent.bookings.forEach { booking ->
                if (currentUserId == booking.userAccount.id) {
                    bookingIdCurrentUser = booking.id
                    binding.btnJoin.visibility = View.GONE
                    binding.btnCancelBooking.visibility = View.VISIBLE

                    if (booking.isPending)
                        binding.tvStatusPending.visibility = View.VISIBLE

                    if (booking.isAccepted)
                        binding.tvStatusConfirmed.visibility = View.VISIBLE
                }
            }
        }


        viewModel.codeLiveData.observe(this) { code ->
            if (code == CODE_201 || code == CODE_204) {
                sharedViewModel.refreshListByCategoryId(args.activityEvent.category.id)
                DetailsActivityFragmentDirections
                    .actionDetailsActivityFragmentToProfileFragment().let {
                        findNavController().navigate(it)
                    }
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsActivityBinding.inflate(inflater, container, false)

        with(args.activityEvent) {

            binding.tvTitle.text = title
            binding.tvDate.text = dateFormatter(startAt)
            binding.tvLocation.text = location
            binding.tvDescription.text = description
            binding.tvUsername.text = creator.username
            binding.tvCategory.text = category.name
            binding.tvMaxPeople.text = requireContext().getString(R.string.max_people)
                .format(maxOfPeople - bookings.count())

            myPicassoFun(creator.imageUrl, binding.civUserImage)

            if (maxOfPeople <= bookings.count()) {
                binding.tvNoMoreBookings.visibility = View.VISIBLE
                binding.btnJoin.visibility = View.GONE
            }

            binding.civUserImage.setOnClickListener {
                DetailsActivityFragmentDirections
                    .actionDetailsActivityFragmentToProfileFragment(creator.id)
                    .let {
                        findNavController().navigate(it)
                    }
            }

            binding.iconValidation.setOnClickListener {
                DetailsActivityFragmentDirections
                    .actionDetailsActivityFragmentToValidationListFragment(id)
                    .let {
                        findNavController().navigate(it)
                    }

            }

        }

        // DISPLAY AVATAR OF USERS HOW ARE PARTICIPANTS
        args.activityEvent.bookings.forEach { booking ->
            val density = requireContext().resources.displayMetrics.density
            val circleImageView = CircleImageView(requireContext())
            val params = LinearLayout.LayoutParams((65 * density).toInt(), (65 * density).toInt())
            circleImageView.layoutParams = params
            circleImageView.setPadding((8 * density).toInt(), 0, 0, 0)

            myPicassoFun(booking.userAccount.imageUrl, circleImageView)
            binding.linearLayoutAvatar.addView(circleImageView)

            if(booking.isAccepted) {
                circleImageView.borderWidth = 4
                circleImageView.borderColor = requireContext().getColor(R.color.my_green)
            } else {
                circleImageView.borderWidth = 4
                circleImageView.borderColor = requireContext().getColor(R.color.my_orange)
            }

            circleImageView.setOnClickListener {
                DetailsActivityFragmentDirections
                    .actionDetailsActivityFragmentToProfileFragment(booking.userAccount.id)
                    .let {
                        findNavController().navigate(it)
                    }
            }

        }


        binding.btnJoin.setOnClickListener() {
            ConfirmActionDialog(
                onConfirm = {
                    viewModel
                        .createBooking(args.activityEvent.id.toHydraActivitiesId())
                },
                resId = R.string.comfirm_booking
            ).show(childFragmentManager, ConfirmActionDialog.TAG)

        }

        binding.btnCancelBooking.setOnClickListener {
            ConfirmActionDialog(
                onConfirm = {
                    viewModel
                        .cancelBooking(bookingIdCurrentUser ?: 0)
                },
                resId = R.string.comfirm_cancel
            ).show(childFragmentManager, ConfirmActionDialog.TAG)
        }

        binding.btnEdit.setOnClickListener {
            DetailsActivityFragmentDirections
                .actionDetailsActivityFragmentToActivityEventFormFragment(args.activityEvent)
                .let {
                    findNavController().navigate(it)
                }
        }

        binding.btnDelete.setOnClickListener {
            ConfirmActionDialog(
                onConfirm = {
                    viewModel
                        .deleteActivity(args.activityEvent.id)
                },
                resId = R.string.confirm_delete
            ).show(childFragmentManager, ConfirmActionDialog.TAG)
        }



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
package com.example.administrator.travel.views.fragments;

/**
 * Created by Henry
 */

//public class ChatSearchFriendsFragment extends Fragment implements SearchFriendView {
//
//    SharedPreferences sharedPreferences;
//    EditText edtSearch;
//    CustomRecyclerSearchAdapter adapter;
//    RecyclerView mResultList;
//    public List<UserInformation> listUserQuery = new ArrayList<>();
//    public List<String> keyUser = new ArrayList<>();
//
//    ChatSearchFriendPresenter chatSearchFriendPresenter;
//
//    public ChatSearchFriendsFragment() {
//        // Required empty public constructor
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_chat_search_friends, container, false);
//
//        edtSearch = (EditText) view.findViewById(R.id.edt_chat_contact_search);
//        mResultList = (RecyclerView) view.findViewById(R.id.rv_chat_contact_friendearch);
//
//        //set adapter
//        adapter = new CustomRecyclerSearchAdapter(listUserQuery);
//        mResultList.setAdapter(adapter);
//        mResultList.setLayoutManager(new LinearLayoutManager(getActivity()));
//        adapter.notifyDataSetChanged();
////       presenter
//        chatSearchFriendPresenter = new ChatSearchFriendPresenterImpl(this);
//
////       sharedPreferences
//        sharedPreferences = getActivity().getSharedPreferences("dataLogin",android.content.Context.MODE_PRIVATE);
//
//        chatSearchFriendPresenter.getFriendInvitationPresenter(sharedPreferences);
////
//        edtSearch.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                final int DRAWABLE_RIGHT = 2;
//                if(event.getAction() == MotionEvent.ACTION_UP) {
//                    if(event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        listUserQuery.clear();
//                        keyUser.clear();
//                        chatSearchFriendPresenter.getSearchFriendPresenter(edtSearch.getText().toString());
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//
//        adapter.setOnItemClickedListener(new CustomRecyclerSearchAdapter.OnItemClickedListener() {
//            @Override
//            public void onItemFriendClick(int index) {
//
//                UserInformation user = listUserQuery.get(index);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("name",user.getName());
//                bundle.putString("sex",user.getSex());
//                bundle.putString("sdt",user.getSdt());
//                bundle.putString("mail",user.getMail());
//                bundle.putString("urlAvatar",user.getUrlAvatar());
//
//                bundle.putString("UserID",keyUser.get(index));
//                InfoUserSearchFragment infoUserSearchFragment = new InfoUserSearchFragment();
//
//                infoUserSearchFragment.setArguments(bundle);
//
//                android.app.FragmentManager manager = getFragmentManager();
//                manager.beginTransaction().replace(R.id.conten_layout_chatlistfriend, infoUserSearchFragment,
//                        infoUserSearchFragment.getTag()).addToBackStack("ChatSearch").commit();
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
////        if (context instanceof OnFragmentInteractionListener) {
////            mListener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
////        mListener = null;
//    }
//
//    @Override
//    public void ShowListUserSearch(List<UserInformation> listUserSearch, List<String> UID) {
//
//        listUserQuery.clear();
//        keyUser.clear();
//        for (int i = 0; i < listUserSearch.size(); ++i) {
//            listUserQuery.add(listUserSearch.get(i));
//            keyUser.add(UID.get(i));
//            adapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void ShowFriendInvitation(List<UserInformation> listUserSearch, List<String> UID) {
//
//        listUserQuery.clear();
//        keyUser.clear();
//        for (int i = 0; i < listUserSearch.size(); ++i) {
//            listUserQuery.add(listUserSearch.get(i));
//            keyUser.add(UID.get(i));
//            adapter.notifyDataSetChanged();
//        }
//    }
//
////    onBackPressed
//}

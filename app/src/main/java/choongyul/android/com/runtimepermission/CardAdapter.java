package choongyul.android.com.runtimepermission;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * 리사이클러뷰 구현순서
 * 1. 홀더에 사용하는 위젯을 모두 정의한다.
 * 2. getItemCount 에 데이터 총 개수 전달
 * 3. onCreateViewHolder 에서 뷰 아이템 생성
 * 4. onBindViewHolder 데이터를 꺼내서 세팅한다.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CustomViewHolder> {

    ArrayList<User> datas;
    Context context;
    String temp = "";

    public CardAdapter(ArrayList<User> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                                    // 이전에는 context 자리에 parent.getContext() 가 들어갔는데 우리는 생성자에 context를 받아왔기 때문에 바로 사용이 가능하다.
        View view = LayoutInflater.from(context).inflate(R.layout.list_card_item, parent, false);
        CustomViewHolder cvh = new CustomViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // 데이터를 행 단위로 꺼낸다
        final User user = datas.get(position);

        // 홀더에 데이터를 세팅한다.
        holder.txtNo.setText(user.getNo() + "  ");
        holder.txtName.setText(user.getName()+"  ");
        holder.txtNumber.setText(user.getPhoneNumber().get(0) + "");
        temp = holder.txtNumber.getText().toString();

//        Log.d("Adapter","data user.getName()="+user.getName());

        // 아래와 같이 하면 리스너를 계속 생성한다.
//        holder.callImgBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if ( context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//                            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + temp));
//                            context.startActivity(intent);
//                        }
//                } else {
//                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + temp));
//                    context.startActivity(intent);
//                }
//                context.startActivity(intent);
//            }
//        });

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.cardView.setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter","data size="+datas.size());
        return datas.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView txtNo, txtName, txtNumber;
        CardView cardView;
        ImageButton callImgBtn;

        public CustomViewHolder(View view) {

            super(view);

            txtNo = (TextView) view.findViewById(R.id.txtNo);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtNumber = (TextView) view.findViewById(R.id.txtNumber);
            cardView = (CardView) view.findViewById(R.id.cardView);
            callImgBtn = (ImageButton) view.findViewById(R.id.callImgBtn);

            callImgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if ( context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + temp));
                        }
                    } else {
                        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + temp));
                    }
                    context.startActivity(intent);
                }
            });
        }
    }
}

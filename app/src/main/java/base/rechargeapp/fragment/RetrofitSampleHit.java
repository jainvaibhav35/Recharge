package base.rechargeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.rechargeapp.R;
import base.rechargeapp.appInterface.RetrofitInterface;
import base.rechargeapp.beans.PostRequest.Example;
import base.rechargeapp.beans.StackOverflowQuestions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lin on 2/8/16.
 */

public class RetrofitSampleHit extends Fragment implements Callback<StackOverflowQuestions>, View.OnClickListener {

    private View mView;
    private TextView response;
    private Button hitButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.retrofit_sample, container, false);
        response = (TextView) mView.findViewById(R.id.txt_response);
        hitButton = (Button) mView.findViewById(R.id.btn_click);

        hitButton.setOnClickListener(this);

        return mView;
    }


    @Override
    public void onClick(View mView) {

        switch (mView.getId()) {
            case R.id.btn_click:
                try {

                    // Calling API with Get RegisterRequest
                    hitGetAPI();

                    // Calling API with Post RegisterRequest
                     hitPostRequestAPI();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Retrofit hit " +
                "");
    }

    /**
     * Method to hit API with GET RegisterRequest
     *
     * @throws Exception -- call.enqueue method throws Exception
     */
    private void hitGetAPI() throws Exception {

        response.setText("Getting data please wait....");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                // base url
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        // call to use @Query annotation
        Call<StackOverflowQuestions> call = retrofitInterface.loadQuestions("android");

        // asynchronous calls for synchronous call use call.execute() method
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<StackOverflowQuestions> call, Response<StackOverflowQuestions> response) {
        try {
            this.response.setText(Html.fromHtml(response.body().items.get(0).getTitle()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(Call<StackOverflowQuestions> call, Throwable t) {
        response.setText("some error occurred... please try again...");
        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
    }


    /**
     * Method to hit API request with POST Method
     */
    private void hitPostRequestAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://stagecontent.tk")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Example> call = retrofitInterface.hitService("2");

        //asynchronous call

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                response.body().getData();
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

package com.simplestepapp.network;


import com.simplestepapp.data.Exercise;
import com.simplestepapp.data.UserExercise;
import com.simplestepapp.data.offline.User;
import com.simplestepapp.models.UserExerciseMaster;

import java.util.List;

/**
 * Created by Suren Reddy on 24-Jul-17.
 */

public interface INetwork  {
    void getUsers(String accessToken, MyCallBack<List<User>> simpleCallBack);

    void getExercises(MyCallBack<List<Exercise>> simpleCallBack);

    void getUserExerciseMasters(String userId, MyCallBack<List<UserExerciseMaster>> simpleCallBack);

    void getUserExercises(String masterId, MyCallBack<List<UserExercise>> simpleCallBack);

    void insertUserExercise(UserExercise insertUserExerciseData, LakmeCallBack<UserExercise> insertUserExerciseDataLakmeCallBack);

    void insertUserExerciseMaster(UserExerciseMaster insertUserExerciseMasterData, LakmeCallBack<UserExerciseMaster> insertUserExerciseMasterDataLakmeCallBack);

    void updateUserExerciseMaster(String id, UserExerciseMaster bannerUploadRowData, LakmeCallBack<UserExerciseMaster> bannerUploadRowDataLakmeCallBack);
}
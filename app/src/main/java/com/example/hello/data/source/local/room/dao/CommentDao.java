package com.example.hello.data.source.local.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hello.data.source.local.room.model.CommentEntity;
import com.example.hello.data.source.local.room.model.SenderEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    Flowable<List<CommentEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(CommentEntity commentEntity);
}

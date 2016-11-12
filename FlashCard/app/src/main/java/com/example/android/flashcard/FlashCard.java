package com.example.android.flashcard;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Viet on 11/12/2016.
 */
public class FlashCard implements Parcelable{
    String question,answer,collection;
    int id;
    //Create when load into database
    public FlashCard (String q,String ans,String col,int id) {
        question=q;
        answer=ans;
        collection=col;
        this.id=id;
    }
    //Create new
    public FlashCard (String q, String ans, String collection) {
        question=q;
        answer=ans;
        this.collection=collection;
        //Add into database
        //Get rowid
        //Set id=rowid
    }
    public FlashCard(Parcel parcel) {
        id=parcel.readInt();
        question=parcel.readString();
        answer=parcel.readString();
        collection=parcel.readString();
    }
    @Override
    public int describeContents() {
        return  0;
    }
    @Override
    public  void writeToParcel(Parcel d, int flags) {
        d.writeInt(id);
        d.writeString(question);
        d.writeString(answer);
        d.writeString(collection);
    }
    public  final Parcelable.Creator<FlashCard> CREATOR= new Parcelable.Creator<FlashCard>(){
        @Override
        public  FlashCard createFromParcel(Parcel parcel) {
            return  new FlashCard(parcel);
        }
        @Override
        public  FlashCard[] newArray(int i) {
            return new FlashCard[i];
        }
    };
}

package com.henallux.walkandpick.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Search implements Parcelable {
    private String difficulty;
    private String kilometer;
    private String idPlace;

    public Search(String difficulty, String kilometer, String idPlace)
    {
        this.difficulty = difficulty;
        this.kilometer = kilometer;
        this.idPlace = idPlace;
    }

    public String getDifficulty(){
        return difficulty;
    }
    public String getKilometer(){
        return kilometer;
    }
    public String getIdPlace(){
        return idPlace;
    }

    // Parcelling part
    public Search(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.difficulty = data[0];
        this.kilometer = data[1];
        this.idPlace = data[2];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.difficulty,
                this.kilometer,
                this.idPlace});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}

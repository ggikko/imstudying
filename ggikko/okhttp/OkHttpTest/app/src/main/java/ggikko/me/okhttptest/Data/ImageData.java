package ggikko.me.okhttptest.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ggikko (JiHong Park) on 2017. 4. 23..
 */

public class ImageData {

    public Channel channel;

    public class Channel {
        @SerializedName("result")
        @Expose
        public String result;
        @SerializedName("pageCount")
        @Expose
        public String pageCount;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("totalCount")
        @Expose
        public String totalCount;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("item")
        @Expose
        public List<Item> item;
        @SerializedName("lastBuildDate")
        @Expose
        public String lastBuildDate;
        @SerializedName("link")
        @Expose
        public String link;
        @SerializedName("generator")
        @Expose
        public String generator;

        public class Item {
            @SerializedName("pubDate")
            @Expose
            public String pubDate;
            @SerializedName("title")
            @Expose
            public String title;
            @SerializedName("thumbnail")
            @Expose
            public String thumbnail;
            @SerializedName("cp")
            @Expose
            public String cp;
            @SerializedName("height")
            @Expose
            public String height;
            @SerializedName("link")
            @Expose
            public String link;
            @SerializedName("width")
            @Expose
            public String width;
            @SerializedName("image")
            @Expose
            public String image;
            @SerializedName("cpname")
            @Expose
            public String cpname;
        }
    }
}

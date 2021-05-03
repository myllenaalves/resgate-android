import com.google.gson.annotations.SerializedName

data class Pets(
        @SerializedName("id")
        var id: Int?,
        @SerializedName("title")
        var title: String,
        @SerializedName("number")
        var number: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("image")
        var image: String
)


import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore

class DatabaseHelper(context: Context) {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun insertUser(username: String, email: String, password: String, callback: (Long) -> Unit) {
        val user = hashMapOf(
            "username" to username,
            "email" to email,
            "password" to password
        )

        firestore.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                // Handle successful insertion
                val userId = documentReference.id
                // You can store the userId in your local database if needed
                callback(userId.toLong())
            }
            .addOnFailureListener { exception ->
                // Handle insertion failure
                // Log the error or show an error message to the user
                callback(-1L)
            }
    }
}

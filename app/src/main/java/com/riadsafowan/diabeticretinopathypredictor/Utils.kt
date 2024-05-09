package com.riadsafowan.diabeticretinopathypredictor

import android.content.Context
import android.net.Uri
import okhttp3.MultipartBody
import java.io.File
import java.io.InputStream

class Utils {
    fun uriToFile(uri: Uri, context: Context): File {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_image_file")
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
//    fun fileToMultipartBodyPart(file: File, fieldName: String): MultipartBody.Part {
//        val requestFile =
//            file.asRequestBody("image/*".toMediaTypeOrNull()) // Specify media type as per your image type
//        return MultipartBody.Part.createFormData(fieldName, file.name, requestFile)
//    }
}
import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:attendance_movil/models/client.dart';
import 'package:image_picker/image_picker.dart';
import 'package:image/image.dart' as img;
import 'package:intl/intl.dart';



class ApiAttendance {
  final String baseUrl = "https://d261b165d29a.ngrok.app"; //

  Future<String> enviarImagen(XFile imagen,String empleadoId) async {
    var uri = Uri.parse('$baseUrl/minio/attendance/upload');


    DateTime ahora = DateTime.now();
    String fecha = DateFormat('yyyy-MM-dd HH:mm').format(ahora);

    String imageName= "$empleadoId-$fecha.jpg";

    var request = http.MultipartRequest('POST', uri);

    File imagenReducida = reduceImageSize(File(imagen.path), width: 500);

    request.files.add(
      http.MultipartFile(
        'file',
        imagenReducida.readAsBytes().asStream(),
        imagenReducida.lengthSync(),
        filename: imageName,
      ),
    );

    var response = await request.send();
    return imageName;
  }


  Future<bool> enviarDatos(String empleadoId, Client cliente, String imagen) async {

    final url = '$baseUrl/attendance/attendance/save';
    final headers = {
      "Content-Type": "application/json",
    };

    final body = json.encode({
      "idEmployee": empleadoId,
      "idCustomer": cliente.customerId,
      "photo": imagen, // Codificando la imagen en base64.
    });

    print(body);

    final response = await http.post(
      Uri.parse(url),
      headers: headers,
      body: body,
    );

    if (response.statusCode == 200) {
      return true;
    } else {
      throw Exception("Error al enviar los datos: ${response.body}");
    }
  }


  File reduceImageSize(File imageFile, {int? width, int? height}) {
    final originalImage = img.decodeImage(imageFile.readAsBytesSync());

    final resizedImage = img.copyResize(originalImage!, width: width, height: height);

    final tempDir = Directory.systemTemp;
    final targetPath = "${tempDir.path}/temp_resized_image.jpg";
    final targetFile = File(targetPath);
    targetFile.writeAsBytesSync(img.encodeJpg(resizedImage));

    return targetFile;
  }
}
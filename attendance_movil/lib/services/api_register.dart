import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:attendance_movil/models/client.dart';

class ApiRegister {
  static const String baseUrl = 'https://d261b165d29a.ngrok.app';

  Future<List<Client>> getClients() async {
    final response = await http.get(Uri.parse('$baseUrl/register/customers'));

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((client) => Client.fromJson(client)).toList();
    } else {
      throw Exception('Falló la carga de clientes desde la API');
    }
  }
}
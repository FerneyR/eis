import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import 'package:attendance_movil/models/client.dart';
import 'package:attendance_movil/services/api_register.dart';
import 'package:attendance_movil/services/api_attendance.dart';


void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Attendance',
      theme: ThemeData(
        primarySwatch: Colors.indigo,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  TextEditingController _empleadoController = TextEditingController();
  Client? _clienteSeleccionado;
  XFile? _image;

  late Future<List<Client>> _listaClientes;

  @override
  void initState() {
    super.initState();
    _listaClientes = ApiRegister().getClients();
  }

  _enviarInformacion() async {
    try {

      String imagenEnviada = await ApiAttendance().enviarImagen(_image!, _empleadoController.text);

      if (imagenEnviada.isEmpty) {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text("Error al enviar la imagen.")));
        return;
      }

      bool exito = await ApiAttendance().enviarDatos(
        _empleadoController.text,
        _clienteSeleccionado!,
        imagenEnviada,
      );
      if (exito) {
        ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text("Datos enviados con éxito.")));
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text("Error: $e")));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Attendance'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Icon(
                  Icons.engineering,
                ),
                SizedBox(width: 6),
                Text("Selecciona tu empresa"),
              ],
            ),
            FutureBuilder<List<Client>>(
              future: _listaClientes,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.done) {
                  if (snapshot.hasError) {
                    return Text("Error: ${snapshot.error}");
                  }
                  final clientes = snapshot.data;
                  return DropdownButton<Client>(
                    value: _clienteSeleccionado,
                    items: clientes!.map((Client cliente) {
                      return DropdownMenuItem<Client>(
                        value: cliente,
                        child: Text(cliente.name),
                      );
                    }).toList(),
                    onChanged: (Client? nuevoCliente) {
                      setState(() {
                        _clienteSeleccionado = nuevoCliente;
                      });
                    },
                    hint: Text('Selecciona un cliente'),
                  );
                } else {
                  return CircularProgressIndicator();
                }
              },
            ),
            SizedBox(height: 16),
            TextField(
              controller: _empleadoController,
              decoration: InputDecoration(labelText: 'ID Empleado'),
              keyboardType: TextInputType.number,
            ),
            SizedBox(height: 16),
            ElevatedButton(
                onPressed: () async {
                  XFile? image = await ImagePicker().pickImage(
                      source:
                          ImageSource.camera); // Modificado para usar la cámara
                  if (image != null) {
                    setState(() {
                      _image = image;
                    });
                  }
                },
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Icon(
                      Icons.photo_camera,
                    ),
                    SizedBox(width: 6),
                    Text("Tomar foto"),
                  ],
                )
                // Texto modificado
                ),
            SizedBox(height: 16),
            _image != null
                ? Image.file(
                    File(_image!.path),
                    width: 200,
                    height: 200,
                  )
                : Text('No se ha tomado una foto'),
            ElevatedButton(
              onPressed: _enviarInformacion,
              child: Text('Enviar Datos'),
            ),
          ],

        ),
      ),
    );
  }
}

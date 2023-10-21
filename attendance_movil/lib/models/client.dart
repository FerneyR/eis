class Client {
  final int customerId;
  final String name;
  final String location;

  Client({required this.customerId, required this.name, required this.location});

  factory Client.fromJson(Map<String, dynamic> json) {
    return Client(
      customerId: json['customerId'],
      name: json['name'],
      location: json['location'],
    );
  }
}